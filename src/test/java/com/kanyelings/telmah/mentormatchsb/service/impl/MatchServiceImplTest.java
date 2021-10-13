package com.kanyelings.telmah.mentormatchsb.service.impl;

import com.kanyelings.telmah.mentormatchsb.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.entity.MentorEntity;
import com.kanyelings.telmah.mentormatchsb.repository.MatchRepository;
import com.kanyelings.telmah.mentormatchsb.repository.MenteeRepository;
import com.kanyelings.telmah.mentormatchsb.repository.MentorRepository;
import com.kanyelings.telmah.mentormatchsb.service.MatchService;
import lombok.AllArgsConstructor;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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