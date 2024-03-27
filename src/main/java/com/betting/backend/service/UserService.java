package com.betting.backend.service;

import com.betting.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String testConnection() {
        return userRepository.testConnection();
    }
}
