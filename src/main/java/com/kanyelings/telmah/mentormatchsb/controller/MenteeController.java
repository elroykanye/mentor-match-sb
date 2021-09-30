package com.kanyelings.telmah.mentormatchsb.controller;

import com.kanyelings.telmah.mentormatchsb.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.service.MenteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/mentee")
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
