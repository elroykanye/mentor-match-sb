package com.kanyelings.telmah.mentormatchsb.service.impl;

import com.kanyelings.telmah.mentormatchsb.data.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.data.entity.MentorEntity;
import com.kanyelings.telmah.mentormatchsb.data.repository.MatchRepository;
import com.kanyelings.telmah.mentormatchsb.data.repository.MenteeRepository;
import com.kanyelings.telmah.mentormatchsb.data.repository.MentorRepository;
import com.kanyelings.telmah.mentormatchsb.business.service.MatchService;
import lombok.AllArgsConstructor;

import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@SpringBootTest
@AllArgsConstructor
public class MatchServiceImplTest {
    private final MatchRepository matchRepository;
    private final MentorRepository mentorRepository;
    private final MenteeRepository menteeRepository;
    private final MatchService matchService;
    private static List<MenteeEntity> mentees;
    private static List<MentorEntity> mentors;


}