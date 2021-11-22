package com.kanyelings.telmah.mentormatchsb.api.controller;

import com.kanyelings.telmah.mentormatchsb.api.dto.MentorDto;
import com.kanyelings.telmah.mentormatchsb.business.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/mentor")
public class MentorController {
    private final MentorService mentorService;
    @Autowired
    public MentorController(MentorService mentorService){
        this.mentorService = mentorService;
    }

    @GetMapping("/{mentorId}")
    public ResponseEntity<?> getMentorById(@PathVariable("mentorId") Long mentorId) {
        return mentorService.getMentorById(mentorId);
    }

    @GetMapping
    public ResponseEntity<List<MentorDto>> getAllMentors(){
        return mentorService.getAllMentors();
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> addMentor(@ModelAttribute MentorDto newMentor){
        return mentorService.addNewMentor(newMentor);
    }
}
