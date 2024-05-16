package com.betting.backend.model.dto;

import lombok.Data;

@Data
public class BetRequest {

    private Integer matchId;

    private Integer userId;

    private String result;

    private Integer amount;
}
