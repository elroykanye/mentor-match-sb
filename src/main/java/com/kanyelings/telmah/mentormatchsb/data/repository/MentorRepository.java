package com.kanyelings.telmah.mentormatchsb.data.repository;

import com.kanyelings.telmah.mentormatchsb.data.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.data.entity.MentorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MentorRepository extends JpaRepository<MentorEntity, Long> {
    Optional<MentorEntity> findByUsernameOrEmail(String username, String email);
}
