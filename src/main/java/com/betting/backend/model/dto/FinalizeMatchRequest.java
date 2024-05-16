package com.betting.backend.model.dto;

import lombok.Data;

@Data
public class FinalizeMatchRequest {

    private Integer matchId;

    private String result;

}
