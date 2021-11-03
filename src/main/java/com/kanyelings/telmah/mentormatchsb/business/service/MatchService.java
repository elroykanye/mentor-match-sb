package com.kanyelings.telmah.mentormatchsb.business.service;

import com.kanyelings.telmah.mentormatchsb.api.dto.MatchDto;
import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MatchService {
    ResponseEntity<String> shuffleMatches();

    ResponseEntity<List<MatchDto>> getAllMatches();

    ResponseEntity<List<MenteeDto>> getAllMenteesByMentorId(Long mentorId);

    ResponseEntity<?> getMentorByMenteeId(Long menteeId);
}
