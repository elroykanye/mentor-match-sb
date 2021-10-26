package com.kanyelings.telmah.mentormatchsb.api.controller;

import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import com.kanyelings.telmah.mentormatchsb.business.service.MenteeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/api/mentee")
@AllArgsConstructor
public class MenteeController {
    private final MenteeService menteeService;

    @GetMapping(value = "/all")
    public List<MenteeDto> getAllMentees(){
        return menteeService.getAllMentees();
    }

    @PostMapping(value = "/add")
    public void addMentee(@RequestBody MenteeDto newMentee){
        menteeService.addNewMentee(newMentee);
    }
}
