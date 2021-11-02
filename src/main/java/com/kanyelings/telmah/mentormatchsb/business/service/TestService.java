package com.kanyelings.telmah.mentormatchsb.business.service;

import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import com.kanyelings.telmah.mentormatchsb.api.dto.MentorDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TestService {
    ResponseEntity<Map<MentorDto, List<MenteeDto>>> shuffleTest();
}
