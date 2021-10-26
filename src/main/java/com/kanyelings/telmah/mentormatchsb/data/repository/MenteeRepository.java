package com.kanyelings.telmah.mentormatchsb.data.repository;

import com.kanyelings.telmah.mentormatchsb.data.entity.MenteeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenteeRepository extends JpaRepository<MenteeEntity, Long> {
}
