package com.kanyelings.telmah.mentormatchsb.controller;

import com.kanyelings.telmah.mentormatchsb.entity.MatchEntity;
import com.kanyelings.telmah.mentormatchsb.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/match")
@AllArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @PutMapping("/shuffle")
    public ResponseEntity<String> shuffle() {
        return matchService.shuffleMatches();
    }

    public ResponseEntity<List<MatchEntity>> getAll() {
        return matchService.getAllMatches();
    }
}
