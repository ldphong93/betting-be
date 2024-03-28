package com.betting.backend.controller;

import com.betting.backend.model.dto.LoginRequest;
import com.betting.backend.model.dto.LoginResponse;
import com.betting.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j(topic = "UserController")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/hello")
    public String helloWorld() {
        log.info("Receive hello connection!");
        return userService.testConnection();
    }

    @GetMapping(value = "/login")
    public ResponseEntity<LoginResponse> loggingIn(@RequestBody LoginRequest request) {
        log.info("Receive login request with username [{}]", request.getUsername());
        return userService.login(request);
    }
}
