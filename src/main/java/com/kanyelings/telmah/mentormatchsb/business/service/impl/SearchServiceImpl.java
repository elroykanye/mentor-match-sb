package com.kanyelings.telmah.mentormatchsb.business.service.impl;

import com.kanyelings.telmah.mentormatchsb.business.model.EntitySearch;
import com.kanyelings.telmah.mentormatchsb.business.model.enums.Role;
import com.kanyelings.telmah.mentormatchsb.business.service.SearchService;
import com.kanyelings.telmah.mentormatchsb.data.repository.MenteeRepository;
import com.kanyelings.telmah.mentormatchsb.data.repository.MentorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
                    log.info("searching mentees");
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

        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }
}