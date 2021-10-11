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

    @BeforeAll
    static void setupEntities() {
         mentees = List.of(
                MenteeEntity.builder().firstName("Elroy").secondName("Kanye").department("COME").gender("M").build(),
                MenteeEntity.builder().firstName("Andrew").secondName("Tatah").department("COME").gender("M").build(),
                MenteeEntity.builder().firstName("Ngumih").secondName("Fienne").department("COME").gender("F").build(),
                MenteeEntity.builder().firstName("Joy").secondName("Nalle").department("CVLE").gender("F").build(),
                MenteeEntity.builder().firstName("Ngwa").secondName("Jude").department("COME").gender("M").build(),
                MenteeEntity.builder().firstName("Alouzeh").secondName("Brandone").department("COME").gender("M").build(),
                MenteeEntity.builder().firstName("Ankini").secondName("Muso").department("COME").gender("F").build()
        );

         mentors = List.of(
                MentorEntity.builder().firstName("Njong").secondName("Emy").department("COME").gender("F").build(),
                MentorEntity.builder().firstName("Chelsea").secondName("Banke").department("COME").gender("M").build(),
                MentorEntity.builder().firstName("Ntunyu").secondName("Serge").department("COME").gender("M").build(),
                MentorEntity.builder().firstName("Fombi").secondName("Favour").department("COME").gender("M").build(),
                MentorEntity.builder().firstName("Kelly").secondName("Mba").department("COME").gender("F").build(),
                MentorEntity.builder().firstName("Mofor").secondName("Emma").department("COME").gender("M").build(),
                MentorEntity.builder().firstName("Ngwa").secondName("Emma").department("COME").gender("M").build()
        );

    }

    @Test
    public void shuffleTest() {

        menteeRepository.deleteAll();
        mentorRepository.deleteAll();

        mentorRepository.saveAll(mentors);
        menteeRepository.saveAll(mentees);

        matchService.shuffleMatches();

        matchRepository.findAll().forEach(
                match -> {
                    MentorEntity mentor = mentorRepository.findById(match.getMentorId()).orElseThrow();
                    MenteeEntity mentee = menteeRepository.findById(match.getMenteeId()).orElseThrow();

                    System.out.println(mentor.toString().concat( ":::::" ).concat(mentee.toString()));
                }
        );
    }

}