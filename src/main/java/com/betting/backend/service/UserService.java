package com.betting.backend.service;

import com.betting.backend.model.entity.AppUser;
import com.betting.backend.model.dto.LoginRequest;
import com.betting.backend.model.dto.LoginResponse;
import com.betting.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.betting.backend.config.Constant.*;

@Service
@Slf4j(topic = "UserService")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String testConnection() {
        return userRepository.testConnection();
    }

    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        Optional<AppUser> optionalFoundUser = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());

        if (optionalFoundUser.isPresent()) {
            AppUser user = optionalFoundUser.get();
            LoginResponse response = LoginResponse.builder()
                    .successful(true)
                    .token(user.getRole().equalsIgnoreCase("admin") ? ADMIN_TOKEN : BETTOR_TOKEN)
                    .username(user.getUsername())
                    .role(user.getRole())
                    .build();

            log.info("Login successfully with username [{}]", request.getUsername());
            return ResponseEntity.ok(response);
        } else {
            log.info("Login unsuccessfully with username [{}]", request.getUsername());
            return ResponseEntity.ok(LoginResponse.builder()
                    .successful(false)
                    .build());
        }
    }
}
