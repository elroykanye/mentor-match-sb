package com.kanyelings.telmah.mentormatchsb.controller;

import com.kanyelings.telmah.mentormatchsb.model.EntitySearch;
import com.kanyelings.telmah.mentormatchsb.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/search")
public class SearchController {
    private final SearchService searchService;

    @GetMapping("")
    public ResponseEntity<List<EntitySearch>> searchEntities(@RequestParam("query") String query) {
        return searchService.searchEntities(query);
    }

}
