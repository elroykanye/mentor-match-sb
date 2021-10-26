package com.kanyelings.telmah.mentormatchsb.api.controller;

import com.kanyelings.telmah.mentormatchsb.data.entity.MentorEntity;
import com.kanyelings.telmah.mentormatchsb.business.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<MentorEntity> getAllMentors(){
        return mentorService.getAllMentors();
    }

    @PostMapping(value = "/add")
    public void addMentor(@RequestBody MentorEntity newMentor){
        mentorService.addNewMentor(newMentor);
    }
}
