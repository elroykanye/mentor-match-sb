package com.kanyelings.telmah.mentormatchsb.service;

import com.kanyelings.telmah.mentormatchsb.model.EntitySearch;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SearchService {
    ResponseEntity<List<EntitySearch>> searchEntities(String query);
}
