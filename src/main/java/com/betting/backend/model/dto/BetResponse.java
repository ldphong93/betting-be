package com.betting.backend.model.dto;

import lombok.Data;

@Data
public class BetResponse {

    private boolean successful;

    private String message;

    private Integer balance;

    public BetResponse(String message) {
        this.successful = false;
        this.message = message;
        this.balance = null;
    }

    public BetResponse(String message, Integer balance) {
        this.successful = true;
        this.message = message;
        this.balance = balance;
    }
}
