package com.betting.backend.model.dto;

import com.betting.backend.model.entity.Match;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateMatchResponse {

    private boolean successful;

    private String message;

    private Match match;
}
