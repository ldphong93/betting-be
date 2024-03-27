package com.betting.backend.repository;

import com.betting.backend.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {

    @Query(value = "SELECT 'Hello World'", nativeQuery = true)
    String testConnection();
}
