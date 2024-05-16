package com.betting.backend.repository;

import com.betting.backend.model.entity.BetDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BetDetailRepository extends JpaRepository<BetDetail, Integer> {

    Optional<BetDetail> findByMatchId(Integer matchId);
}
