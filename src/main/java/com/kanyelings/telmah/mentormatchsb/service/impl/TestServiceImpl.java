package com.kanyelings.telmah.mentormatchsb.service.impl;

import com.kanyelings.telmah.mentormatchsb.entity.MatchEntity;
import com.kanyelings.telmah.mentormatchsb.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.entity.MentorEntity;
import com.kanyelings.telmah.mentormatchsb.model.Constants;
import com.kanyelings.telmah.mentormatchsb.repository.MatchRepository;
import com.kanyelings.telmah.mentormatchsb.repository.MenteeRepository;
import com.kanyelings.telmah.mentormatchsb.repository.MentorRepository;
import com.kanyelings.telmah.mentormatchsb.service.MatchService;
import com.kanyelings.telmah.mentormatchsb.service.TestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                MentorEntity.builder().firstName("Tanju").secondName("Bruno").department("COME").gender("M").build(),
                MentorEntity.builder().firstName("Andrew").secondName("Tatah").department("COME").gender("M").build(),
                MentorEntity.builder().firstName("Ngumih").secondName("Fienne").department("COME").gender("F").build(),
                MentorEntity.builder().firstName("Joy").secondName("Ndalle").department("CVLE").gender("F").build(),
                MentorEntity.builder().firstName("Ngwa").secondName("Jude").department("COME").gender("M").build(),
                MentorEntity.builder().firstName("Alouzeh").secondName("Brandone").department("COME").gender("M").build(),
                MentorEntity.builder().firstName("Ankini").secondName("Muso").department("COME").gender("F").build()
        );

        mentees = List.of(
                MenteeEntity.builder().firstName("Njong").secondName("Emy").department("COME").gender("F").build(),
                MenteeEntity.builder().firstName("Chelsea").secondName("Banke").department("COME").gender("M").build(),
                MenteeEntity.builder().firstName("Ntunyu").secondName("Serge").department("COME").gender("M").build(),
                MenteeEntity.builder().firstName("Fombi").secondName("Favour").department("COME").gender("M").build(),
                MenteeEntity.builder().firstName("Kelly").secondName("Mba").department("COME").gender("F").build(),
                MenteeEntity.builder().firstName("Mofor").secondName("Emma").department("COME").gender("M").build(),
                MenteeEntity.builder().firstName("Ngwa").secondName("Emma").department("COME").gender("M").build()
        );

    }

    @Override
    public void shuffleTest() {
        setupEntities();

        menteeRepository.deleteAll();
        mentorRepository.deleteAll();

        mentorRepository.saveAll(mentors);
        menteeRepository.saveAll(mentees);

        matchService.shuffleMatches();

        log.info("Retrieving all matches");
        List<MatchEntity> matchEntities = matchRepository.findAll();

        matchEntities.forEach(
                match -> {
                    Optional<MentorEntity> mentorOption = mentorRepository.findById(match.getMentorId());
                    MentorEntity mentor = mentorOption.orElse(Constants.DEFAULT_MENTOR_COME);

                    MenteeEntity mentee = menteeRepository.findById(match.getMenteeId()).orElseThrow();

                    System.out.println(mentor.getFirstName().concat(mentor.getSecondName())
                            .concat( ":::::" )
                            .concat(mentee.getFirstName().concat(mentee.getSecondName())));
                }
        );
    }
}
