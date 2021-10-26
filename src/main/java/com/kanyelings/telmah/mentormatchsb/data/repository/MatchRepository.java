package com.kanyelings.telmah.mentormatchsb.data.repository;

import com.kanyelings.telmah.mentormatchsb.data.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Long> {
    List<MatchEntity> findAllByMentorId(Long id);

    Optional<MatchEntity> findByMenteeId(Long id);
}
