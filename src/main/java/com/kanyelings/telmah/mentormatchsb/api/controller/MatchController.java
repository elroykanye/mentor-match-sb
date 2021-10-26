package com.kanyelings.telmah.mentormatchsb.api.controller;

import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import com.kanyelings.telmah.mentormatchsb.api.dto.MentorDto;
import com.kanyelings.telmah.mentormatchsb.business.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/match")
@AllArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @PutMapping("/shuffle")
    public ResponseEntity<String> shuffle() {
        return matchService.shuffleMatches();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Map<MentorDto, MenteeDto>>> getAll() {
        return matchService.getAllMatches();
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
