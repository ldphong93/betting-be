package com.betting.backend.Utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MatchResult {
    HOME_WIN("HOME_WIN"),
    DRAW("DRAW"),
    AWAY_WIN("AWAY_WIN");
    private String outcome;

}
