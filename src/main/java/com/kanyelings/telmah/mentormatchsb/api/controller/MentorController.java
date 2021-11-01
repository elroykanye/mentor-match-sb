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


    @GetMapping(value = "/all")
    public List<MentorDto> getAllMentors(){
        return mentorService.getAllMentors();
    }

    @PostMapping(
            value = "/add",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            })
    public ResponseEntity<String> addMentor(@ModelAttribute MentorDto newMentor){
        return mentorService.addNewMentor(newMentor);
    }
}
