package com.betting.backend.model.dto;

import lombok.Data;

@Data
public class CreateMatchRequest {

    private String title;

    private String description;

    private String matchDate;

    private String createdBy;
}
