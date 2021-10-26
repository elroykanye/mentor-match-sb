package com.kanyelings.telmah.mentormatchsb.service.impl;

import com.kanyelings.telmah.mentormatchsb.model.EntitySearch;
import com.kanyelings.telmah.mentormatchsb.model.enums.Role;
import com.kanyelings.telmah.mentormatchsb.repository.MenteeRepository;
import com.kanyelings.telmah.mentormatchsb.repository.MentorRepository;
import com.kanyelings.telmah.mentormatchsb.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final MentorRepository mentorRepository;
    private final MenteeRepository menteeRepository;


    @Override
    public ResponseEntity<List<EntitySearch>> searchEntities(String query) {
        if (query == null || query.equals("")) return new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);

        List<EntitySearch> searchResults = new ArrayList<>();
        menteeRepository.findAll()
                .forEach(menteeEntity -> {
                    if (menteeEntity.getFirstName().contains(query) || menteeEntity.getSecondName().contains(query)) {
                        searchResults.add(EntitySearch.builder()
                                .id(menteeEntity.getMenteeId())
                                .firstName(menteeEntity.getFirstName())
                                .secondName(menteeEntity.getSecondName())
                                .department(menteeEntity.getDepartment())
                                .gender(menteeEntity.getGender())
                                .phoneNumber(menteeEntity.getPhoneNumber())
                                .role(Role.MENTEE)
                                .build()
                        );
                    }
                });
        mentorRepository.findAll()
                .forEach(mentorEntity -> {
                    if (mentorEntity.getFirstName().contains(query) || mentorEntity.getSecondName().contains(query)) {
                        searchResults.add(EntitySearch.builder()
                                .id(mentorEntity.getMentorId())
                                .firstName(mentorEntity.getFirstName())
                                .secondName(mentorEntity.getSecondName())
                                .department(mentorEntity.getDepartment())
                                .gender(mentorEntity.getGender())
                                .phoneNumber(mentorEntity.getPhoneNumber())
                                .role(Role.MENTEE)
                                .build()
                        );
                    }
                });

        return new ResponseEntity<>(searchResults, HttpStatus.FOUND);
    }
}