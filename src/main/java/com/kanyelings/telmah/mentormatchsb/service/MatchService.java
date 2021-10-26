package com.kanyelings.telmah.mentormatchsb.service;

import com.kanyelings.telmah.mentormatchsb.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.entity.MentorEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface MatchService {
    ResponseEntity<String> shuffleMatches();

    ResponseEntity<List<Map<MentorEntity, MenteeEntity>>> getAllMatches();

    ResponseEntity<List<MenteeEntity>> getAllMenteesByMentorId(Long mentorId);

    ResponseEntity<?> getMentorByMenteeId(Long menteeId);
}
