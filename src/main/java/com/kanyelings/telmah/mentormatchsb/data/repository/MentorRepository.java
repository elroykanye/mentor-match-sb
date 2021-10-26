package com.kanyelings.telmah.mentormatchsb.data.repository;

import com.kanyelings.telmah.mentormatchsb.data.entity.MentorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorRepository extends JpaRepository<MentorEntity, Long> {
}
