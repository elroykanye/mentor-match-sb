package com.kanyelings.telmah.mentormatchsb.business.service;

import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MenteeService {
    ResponseEntity<List<MenteeDto>> getAllMentees();

    ResponseEntity<String> addNewMentee(MenteeDto newMentee);

    ResponseEntity<?> getMenteeById(Long menteeId);

    ResponseEntity<String> deleteAllMentees(String secret);
}
