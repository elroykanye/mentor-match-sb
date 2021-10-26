package com.kanyelings.telmah.mentormatchsb.controller;

import com.kanyelings.telmah.mentormatchsb.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.entity.MentorEntity;
import com.kanyelings.telmah.mentormatchsb.model.EntitySearch;
import com.kanyelings.telmah.mentormatchsb.service.MatchService;
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
    public ResponseEntity<List<Map<MentorEntity, MenteeEntity>>> getAll() {
        return matchService.getAllMatches();
    }

    @GetMapping("/{mentorId}/mentees")
    public ResponseEntity<List<MenteeEntity>> getAllMenteesByMentorId(@PathVariable("mentorId") Long mentorId) {
        return matchService.getAllMenteesByMentorId(mentorId);
    }

    @GetMapping("/mentor")
    public ResponseEntity<?> getMentorByMenteeId(@RequestParam("menteeId") Long menteeId) {
        return matchService.getMentorByMenteeId(menteeId);
    }

}
