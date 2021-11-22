package com.kanyelings.telmah.mentormatchsb.api.controller;

import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import com.kanyelings.telmah.mentormatchsb.business.service.MenteeService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/mentee")
public class MenteeController {
    private final MenteeService menteeService;

    @GetMapping(value = "/{menteeId}")
    public ResponseEntity<?> getMenteeById(@PathVariable(value = "menteeId") Long menteeId) {
        return menteeService.getMenteeById(menteeId);
    }

    @GetMapping
    public ResponseEntity<List<MenteeDto>> getAllMentees(){
        return menteeService.getAllMentees();
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> addMentee(@ModelAttribute MenteeDto newMentee){
        return menteeService.addNewMentee(newMentee, newMentee.getImage());
    }
}
