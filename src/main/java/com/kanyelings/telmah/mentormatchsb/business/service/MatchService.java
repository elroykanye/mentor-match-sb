package com.kanyelings.telmah.mentormatchsb.business.service;

import com.kanyelings.telmah.mentormatchsb.data.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.data.entity.MentorEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface MatchService {
    ResponseEntity<String> shuffleMatches();

    ResponseEntity<List<Map<MentorEntity, MenteeEntity>>> getAllMatches();

    ResponseEntity<List<MenteeEntity>> getAllMenteesByMentorId(Long mentorId);

    ResponseEntity<?> getMentorByMenteeId(Long menteeId);
}
