package com.kanyelings.telmah.mentormatchsb.api.controller;

import com.kanyelings.telmah.mentormatchsb.data.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.business.service.MenteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/api/mentee")
public class MenteeController {
    private final MenteeService menteeService;
    @Autowired
    public MenteeController(MenteeService menteeService){
        this.menteeService = menteeService;
    }


    @GetMapping(value = "/all")
    public List<MenteeEntity> getAllMentees(){
        return menteeService.getAllMentees();
    }

    @PostMapping(value = "/add")
    public void addMentee(@RequestBody MenteeEntity newMentee){
        menteeService.addNewMentee(newMentee);
    }
}
