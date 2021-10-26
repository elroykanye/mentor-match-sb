package com.kanyelings.telmah.mentormatchsb.repository;

import com.kanyelings.telmah.mentormatchsb.entity.MentorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorRepository extends JpaRepository<MentorEntity, Long> {
}
