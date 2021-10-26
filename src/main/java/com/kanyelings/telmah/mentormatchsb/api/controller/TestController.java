package com.kanyelings.telmah.mentormatchsb.api.controller;

import com.kanyelings.telmah.mentormatchsb.business.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/test")
public class TestController {
    private final TestService testService;

    @GetMapping(value = "/shuffle")
    public void shuffleTest() {
        testService.shuffleTest();
    }
}
