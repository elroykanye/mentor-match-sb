package com.kanyelings.telmah.mentormatchsb.api.controller;

import com.kanyelings.telmah.mentormatchsb.api.dto.MatchDto;
import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import com.kanyelings.telmah.mentormatchsb.business.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<List<MatchDto>> getAll() {
        return matchService.getAllMatches();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        return matchService.deleteAllMatches();
    }

    @GetMapping("/{mentorId}/mentees")
    public ResponseEntity<List<MenteeDto>> getAllMenteesByMentorId(@PathVariable("mentorId") Long mentorId) {
        return matchService.getAllMenteesByMentorId(mentorId);
    }

    @GetMapping("/mentor")
    public ResponseEntity<?> getMentorByMenteeId(@RequestParam("menteeId") Long menteeId) {
        return matchService.getMentorByMenteeId(menteeId);
    }

}
