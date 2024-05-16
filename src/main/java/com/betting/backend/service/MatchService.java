package com.betting.backend.service;

import com.betting.backend.Utils.MatchResult;
import com.betting.backend.model.dto.*;
import com.betting.backend.model.entity.AppUser;
import com.betting.backend.model.entity.BetDetail;
import com.betting.backend.model.entity.Match;
import com.betting.backend.repository.BetDetailRepository;
import com.betting.backend.repository.MatchRepository;
import com.betting.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@Service
@Slf4j(topic = "MatchService")
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BetDetailRepository betDetailRepository;

    public ResponseEntity<List<Match>> getAllMatchesFrom(String fromDate) {
        List<Match> matches = matchRepository.findByMatchDateFrom(fromDate);
        log.info("Fetch matches list successfully.");
        return ResponseEntity.ok(matches);
    }

    @Transactional
    public ResponseEntity<BetResponse> layBet(BetRequest request) {
        log.info("Received betting request with match id [{}].", request.getMatchId());

        Optional<AppUser> optionalUser = userRepository.findById(request.getUserId());
        if (optionalUser.isEmpty()) {
            log.info("Betting request with user not found.");
            return ResponseEntity.ok(new BetResponse("User not found."));
        }

        AppUser user = optionalUser.get();

        //check if match already finish
        Optional<Match> optionalMatch = matchRepository.findById(request.getMatchId());
        if (optionalMatch.isEmpty()) {
            log.info("Match not exist.");
            return ResponseEntity.ok(new BetResponse(String.format("Match not exist with id [%s].", request.getMatchId())));
        } else {
            Match match = optionalMatch.get();
            Date currentDate = new Date(System.currentTimeMillis());
            if (currentDate.compareTo(match.getMatchDate()) >= 0) {
                log.info("Match already finished.");
                return ResponseEntity.ok(new BetResponse(String.format("Match already finish with id [%s].", request.getMatchId())));
            }
        }

        //check if match already finalized
        Optional<BetDetail> optionalCheckBetDetail = betDetailRepository.findByMatchId(request.getMatchId());
        if (optionalCheckBetDetail.isPresent()) {
            BetDetail checkBetDetail = optionalCheckBetDetail.get();
            if (checkBetDetail.isFinalized()) {
                log.info("Match already finalized.");
                return ResponseEntity.ok(new BetResponse(String.format("Match already finalized with id [%s].", request.getMatchId())));
            }
        }

        //check balance
        if (user.getBalance() < request.getAmount()) {
            log.info("Balance not sufficient.");
            return ResponseEntity.ok(new BetResponse(String.format("Balance not sufficient [%s], please lay bet again.", user.getBalance())));
        }

        //debit bettor account
        user.setBalance(user.getBalance() - request.getAmount());
        log.info("User's account subtracted.");
        userRepository.save(user);

        //add into booker account
        List<AppUser> bookers = userRepository.findByRole("booker");
        if (bookers.isEmpty()) {
            log.info("No booker found, can not bet the match.");
            return ResponseEntity.ok(new BetResponse("No booker found, can not bet the match."));
        }
        AppUser firstBooker = bookers.get(0);
        firstBooker.setBalance(firstBooker.getBalance() + request.getAmount());
        userRepository.save(firstBooker);

        //save detail into betting table
        //bet detail is saved with format: result, userID, betAmount
        String userBetInfo = String.join(",", request.getResult(), request.getUserId().toString(), request.getAmount().toString());

        Optional<BetDetail> optionalBetDetail = betDetailRepository.findByMatchId(request.getMatchId());
        if (optionalBetDetail.isEmpty()) {
            List list = new ArrayList();
            list.add(userBetInfo);
            BetDetail betDetail = BetDetail.builder()
                    .matchId(request.getMatchId())
                    .betInfo(list)
                    .isFinalized(false)
                    .build();
            betDetailRepository.save(betDetail);
        } else {
            BetDetail betDetail = optionalBetDetail.get();
            List<String> bets = new ArrayList<>(betDetail.getBetInfo());
            bets.add(userBetInfo);
            betDetail.setBetInfo(bets);
            betDetailRepository.save(betDetail);
        }

        log.info("Betting request saved successfully.");
        return ResponseEntity.ok(new BetResponse("Betting request saved successfully.", user.getBalance()));
    }

    @Transactional
    public ResponseEntity<String> finalizeMatch(FinalizeMatchRequest request) {
        log.info("Receive match finalization for match id [{}]", request.getMatchId());

        Optional<BetDetail> optionalBetDetail = betDetailRepository.findByMatchId(request.getMatchId());
        if (optionalBetDetail.isEmpty()) {
            log.info("Bet detail finalized for match [{}]", request.getMatchId());
            BetDetail betDetail = BetDetail.builder()
                    .matchId(request.getMatchId())
                    .result(MatchResult.valueOf(request.getResult()))
                    .isFinalized(true)
                    .build();
            betDetailRepository.save((betDetail));
            return ResponseEntity.ok(String.format("Bet detail finalized for match [%s]", request.getMatchId()));
        }

        BetDetail betDetail = optionalBetDetail.get();
        if (betDetail.isFinalized()) {
            log.info("Match with id [{}] already finalized.", request.getMatchId());
            return ResponseEntity.ok(String.format("Match with id [%s] already finalized.", request.getMatchId()));
        }

        List<String> betInfos = betDetail.getBetInfo();
        for (String info : betInfos) {
            //bet detail is saved with format: result, userID, betAmount
            String[] details = info.split(",");
            if (details[0].equals(request.getResult())) {
                Integer userId = parseInt(details[1]);

                //subtract money from booker
                List<AppUser> bookers = userRepository.findByRole("booker");
                if (bookers.isEmpty()) {
                    log.info("No booker found, can not finalize match.");
                    return ResponseEntity.ok("No booker found, can not finalize match.");
                }
                AppUser firstBooker = bookers.get(0);
                firstBooker.setBalance(firstBooker.getBalance() - 2 * parseInt(details[2]));
                userRepository.save(firstBooker);

                Optional<AppUser> optionalUser = userRepository.findById(userId);
                if (optionalUser.isEmpty()) {
                    log.info("Bet detail is saved with non-exist user [{}].", userId);
                    return ResponseEntity.ok(String.format("Bet detail is saved with non-exist user [%s].", userId));
                }

                AppUser user = optionalUser.get();
                user.setBalance(user.getBalance() + 2 * parseInt(details[2]));
                userRepository.save(user);
            }
        }

        //set bet detail finalized
        betDetail.setResult(MatchResult.valueOf(request.getResult()));
        betDetail.setFinalized(true);
        betDetailRepository.save(betDetail);

        log.info("Bet detail finalized for match [{}]", request.getMatchId());
        return ResponseEntity.ok(String.format("Bet detail finalized for match [%s]", request.getMatchId()));
    }

    public ResponseEntity<CreateMatchResponse> addMatch(CreateMatchRequest request) {
        log.info("Match creation request received.");
        Match newMatch = Match.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .matchDate(Date.valueOf(request.getMatchDate()))
                .createdBy(request.getCreatedBy())
                .creationDate(Date.valueOf(LocalDate.now()))
                .build();
        newMatch = matchRepository.save(newMatch);
        CreateMatchResponse response = CreateMatchResponse.builder()
                .successful(true)
                .message("Match creation successful.")
                .match(newMatch)
                .build();
        return ResponseEntity.ok(response);
    }
}
