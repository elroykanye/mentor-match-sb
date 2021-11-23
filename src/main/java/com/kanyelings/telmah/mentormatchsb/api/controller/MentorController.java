package com.kanyelings.telmah.mentormatchsb.api.controller;

import com.kanyelings.telmah.mentormatchsb.api.dto.MentorDto;
import com.kanyelings.telmah.mentormatchsb.business.service.MentorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/mentor")
public class MentorController {
    private final MentorService mentorService;

    @GetMapping("/{mentorId}")
    public ResponseEntity<?> getMentorById(@PathVariable("mentorId") Long mentorId) {
        return mentorService.getMentorById(mentorId);
    }

    @GetMapping
    public ResponseEntity<List<MentorDto>> getAllMentors(){
        return mentorService.getAllMentors();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllMentors(@RequestParam String secret) {
        return mentorService.deleteAllMentors(secret);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addMentor(@RequestBody MentorDto newMentor){
        return mentorService.addNewMentor(newMentor);
    }
}
