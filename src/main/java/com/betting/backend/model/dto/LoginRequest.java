package com.betting.backend.model.dto;

import lombok.Data;

@Data
public class LoginRequest {

    String username;

    String password;
}
