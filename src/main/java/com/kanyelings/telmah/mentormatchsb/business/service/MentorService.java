package com.kanyelings.telmah.mentormatchsb.business.service;

import com.kanyelings.telmah.mentormatchsb.api.dto.MentorDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MentorService {
    ResponseEntity<List<MentorDto>> getAllMentors();

    ResponseEntity<String> addNewMentor(MentorDto newMentor);

    ResponseEntity<?> getMentorById(Long mentorId);

    ResponseEntity<String> deleteAllMentors(String secret);
}
