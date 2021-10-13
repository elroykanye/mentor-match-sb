package com.kanyelings.telmah.mentormatchsb.repository;

import com.kanyelings.telmah.mentormatchsb.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Long> {
}
