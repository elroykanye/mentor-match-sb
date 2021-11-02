package com.kanyelings.telmah.mentormatchsb.api.controller;

import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import com.kanyelings.telmah.mentormatchsb.api.dto.MentorDto;
import com.kanyelings.telmah.mentormatchsb.business.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/test")
public class TestController {
    private final TestService testService;

    @GetMapping(value = "/shuffle")
    public ResponseEntity<Map<MentorDto, List<MenteeDto>>> shuffleTest(@RequestParam String secret) {
        if (!secret.equals("kanyelroy")) {
            return new ResponseEntity<>(Map.of(), HttpStatus.FORBIDDEN);
        }
        return testService.shuffleTest();
    }
}
