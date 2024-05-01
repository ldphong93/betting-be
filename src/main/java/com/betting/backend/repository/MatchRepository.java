package com.betting.backend.repository;

import com.betting.backend.model.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

    @Query(value = "SELECT * FROM match WHERE match_date >= :date ", nativeQuery = true)
    List<Match> findByMatchDateFrom(@Param("date") String matchDate);
}
