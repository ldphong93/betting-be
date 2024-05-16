package com.betting.backend.repository;

import com.betting.backend.model.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {

    @Query(value = "SELECT 'Hello World'", nativeQuery = true)
    String testConnection();

    @Query(value = "SELECT * FROM app_user WHERE username = :username AND password = :password LIMIT 1", nativeQuery = true)
    Optional<AppUser> findByUsernameAndPassword(String username, String password);

    List<AppUser> findByRole(String role);
}
