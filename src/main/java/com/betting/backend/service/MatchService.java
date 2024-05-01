package com.betting.backend.service;

import com.betting.backend.model.entity.Match;
import com.betting.backend.repository.MatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j(topic = "MatchService")
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    public ResponseEntity<List<Match>> getAllMatchesFrom(String fromDate) {
        List<Match> matches = matchRepository.findByMatchDateFrom(fromDate);
        log.info("Fetch matches list successfully");
        return ResponseEntity.ok(matches);
    }
}
