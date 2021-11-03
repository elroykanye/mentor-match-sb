package com.kanyelings.telmah.mentormatchsb.business.service;

import com.kanyelings.telmah.mentormatchsb.api.dto.MatchDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TestService {
    ResponseEntity<List<MatchDto>> shuffleTest();
}
