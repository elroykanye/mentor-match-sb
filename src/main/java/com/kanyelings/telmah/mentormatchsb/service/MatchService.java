package com.kanyelings.telmah.mentormatchsb.service;

import org.springframework.http.ResponseEntity;

public interface MatchService {
    ResponseEntity<String> shuffleMatches();
}
