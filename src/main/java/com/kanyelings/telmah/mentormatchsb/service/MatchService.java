package com.kanyelings.telmah.mentormatchsb.service;

import com.kanyelings.telmah.mentormatchsb.entity.MatchEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MatchService {
    ResponseEntity<String> shuffleMatches();

    ResponseEntity<List<MatchEntity>> getAllMatches();
}
