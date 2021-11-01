package com.kanyelings.telmah.mentormatchsb.api.controller;

import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import com.kanyelings.telmah.mentormatchsb.business.service.MenteeService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping(value = "/api/mentee")
@AllArgsConstructor
public class MenteeController {
    private final MenteeService menteeService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<MenteeDto>> getAllMentees(){
        return menteeService.getAllMentees();
    }

    @PostMapping(
            value = "/add",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            })
    public ResponseEntity<String> addMentee(@ModelAttribute MenteeDto newMentee){
        return menteeService.addNewMentee(newMentee, newMentee.getImage());
    }
}
