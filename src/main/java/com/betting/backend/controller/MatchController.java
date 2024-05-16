package com.betting.backend.controller;

import com.betting.backend.model.dto.*;
import com.betting.backend.model.entity.Match;
import com.betting.backend.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j(topic = "MatchController")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping(value = "/match")
    public ResponseEntity<List<Match>> getAllMatchesFrom(Optional<String> fromDate){
        LocalDate date = fromDate.isPresent() ? LocalDate.parse(fromDate.get()) : LocalDate.now();
        return matchService.getAllMatchesFrom(date.toString());
    }

    @PostMapping(value = "/match/add")
    public ResponseEntity<CreateMatchResponse> createNewMatch(@RequestBody CreateMatchRequest request){
        return matchService.addMatch(request);
    }

    @PostMapping(value = "/match/bet")
    public ResponseEntity<BetResponse> betOnMatch(@RequestBody BetRequest request){
        return matchService.layBet(request);
    }

    @PostMapping(value = "/match/finalize")
    public ResponseEntity<String> finalizeOnMatch(@RequestBody FinalizeMatchRequest request){
        return matchService.finalizeMatch(request);
    }
}
