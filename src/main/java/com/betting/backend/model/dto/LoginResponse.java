package com.betting.backend.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private boolean successful;

    private String token;

    private String username;

    private String role;
}
