package com.kanyelings.telmah.mentormatchsb.repository;

import com.kanyelings.telmah.mentormatchsb.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
}
