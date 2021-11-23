package com.kanyelings.telmah.mentormatchsb.api.controller;

import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import com.kanyelings.telmah.mentormatchsb.business.service.MenteeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/mentee")
public class MenteeController {
    private final MenteeService menteeService;

    @GetMapping(value = "/{menteeId}")
    public ResponseEntity<?> getMenteeById(@PathVariable(value = "menteeId") Long menteeId) {
        log.info("Getting mentee with id: " + menteeId);
        return menteeService.getMenteeById(menteeId);
    }

    @GetMapping
    public ResponseEntity<List<MenteeDto>> getAllMentees(){
        log.info("Getting all mentees");
        return menteeService.getAllMentees();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllMentees(@RequestParam String secret) {
        log.info("Deleting all mentees - secret = " + secret);
        return menteeService.deleteAllMentees(secret);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addMentee(@RequestBody MenteeDto newMentee){
        log.info("Adding new mentee with username: " + newMentee.getUsername());
        return menteeService.addNewMentee(newMentee);
    }
}
