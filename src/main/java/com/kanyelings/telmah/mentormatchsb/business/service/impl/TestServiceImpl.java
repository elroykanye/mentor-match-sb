package com.kanyelings.telmah.mentormatchsb.business.service.impl;

import com.kanyelings.telmah.mentormatchsb.api.dto.MatchDto;
import com.kanyelings.telmah.mentormatchsb.config.Constants;
import com.kanyelings.telmah.mentormatchsb.business.service.MatchService;
import com.kanyelings.telmah.mentormatchsb.business.service.TestService;
import com.kanyelings.telmah.mentormatchsb.data.entity.MatchEntity;
import com.kanyelings.telmah.mentormatchsb.data.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.data.entity.MentorEntity;
import com.kanyelings.telmah.mentormatchsb.data.repository.MatchRepository;
import com.kanyelings.telmah.mentormatchsb.data.repository.MenteeRepository;
import com.kanyelings.telmah.mentormatchsb.data.repository.MentorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {
    private final MatchRepository matchRepository;
    private final MentorRepository mentorRepository;
    private final MenteeRepository menteeRepository;
    private final MatchService matchService;
    private static List<MenteeEntity> mentees;
    private static List<MentorEntity> mentors;

    static void setupEntities() {
        mentors = List.of(
                MentorEntity.builder().username("tanju").firstName("Tanju").secondName("Bruno").department("COME").gender("M").build(),
                MentorEntity.builder().username("andy").firstName("Andrew").secondName("Tatah").department("COME").gender("M").build(),
                MentorEntity.builder().username("ngumih").firstName("Ngumih").secondName("Fienne").department("COME").gender("F").build(),
                MentorEntity.builder().username("joy").firstName("Joy").secondName("Ndalle").department("CVLE").gender("F").build(),
                MentorEntity.builder().username("ngwa").firstName("Ngwa").secondName("Jude").department("COME").gender("M").build(),
                MentorEntity.builder().username("alouz").firstName("Alouzeh").secondName("Brandone").department("COME").gender("M").build(),
                MentorEntity.builder().username("ankini").firstName("Ankini").secondName("Muso").department("COME").gender("F").build()
        );

        mentees = List.of(
                MenteeEntity.builder().username("njong").firstName("Njong").secondName("Emy").department("COME").gender("F").build(),
                MenteeEntity.builder().username("chel").firstName("Chelsea").secondName("Banke").department("COME").gender("M").build(),
                MenteeEntity.builder().username("serge").firstName("Ntunyu").secondName("Serge").department("COME").gender("M").build(),
                MenteeEntity.builder().username("fombi").firstName("Fombi").secondName("Favour").department("COME").gender("M").build(),
                MenteeEntity.builder().username("kelly").firstName("Kelly").secondName("Mba").department("COME").gender("F").build(),
                MenteeEntity.builder().username("mofor").firstName("Mofor").secondName("Emma").department("COME").gender("M").build(),
                MenteeEntity.builder().username("emma").firstName("Ngwa").secondName("Emma").department("COME").gender("M").build()
        );

    }

    @Override
    public ResponseEntity<List<MatchDto>> shuffleTest() {
        setupEntities();

        menteeRepository.deleteAll();
        mentorRepository.deleteAll();

        mentorRepository.saveAll(mentors);
        menteeRepository.saveAll(mentees);

        matchService.shuffleMatches();

        return matchService.getAllMatches();
    }
}
