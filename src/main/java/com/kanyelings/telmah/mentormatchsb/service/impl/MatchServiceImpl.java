package com.kanyelings.telmah.mentormatchsb.service.impl;

import com.kanyelings.telmah.mentormatchsb.entity.Match;
import com.kanyelings.telmah.mentormatchsb.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.entity.MentorEntity;
import com.kanyelings.telmah.mentormatchsb.model.MatchCombo;
import com.kanyelings.telmah.mentormatchsb.repository.MatchRepository;
import com.kanyelings.telmah.mentormatchsb.repository.MenteeRepository;
import com.kanyelings.telmah.mentormatchsb.repository.MentorRepository;
import com.kanyelings.telmah.mentormatchsb.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final MentorRepository mentorRepository;
    private final MenteeRepository menteeRepository;

    @Override
    public ResponseEntity<String> shuffleMatches() {
        String message = "Shuffle successful";
        HttpStatus status = HttpStatus.OK;

        matchRepository.deleteAll();
        List<MenteeEntity> mentees = menteeRepository.findAll();
        List<MentorEntity> mentors = mentorRepository.findAll();

        List<MatchCombo> matchCombos = shuffleHelper(mentors, mentees);

        matchCombos.forEach(matchCombo -> matchRepository.save(
                Match.builder()
                        .menteeId(matchCombo.getMenteeId())
                        .mentorId(matchCombo.getMentorId())
                        .build()
        ));

        return new ResponseEntity<>(message, status);
    }

    private List<MatchCombo> shuffleHelper(List<MentorEntity> mentors, List<MenteeEntity> mentees) {
        List<MatchCombo> combos = new ArrayList<>();

        mentees.forEach(
                menteeEntity -> {
                    Optional<MentorEntity> mentorOptional = mentors.stream()
                            .filter(mentorEntity -> Objects.equals(menteeEntity.getGender(), mentorEntity.getGender()) &&
                            Objects.equals(menteeEntity.getDepartment(), mentorEntity.getDepartment())).
                            findFirst();
                    mentorOptional.ifPresentOrElse(
                            mentorEntity -> combos.add(
                                    MatchCombo.builder()
                                            .menteeId(menteeEntity.getMenteeId())
                                            .mentorId(mentorEntity.getMentorId())
                                            .build()
                            ),
                            () -> combos.add(MatchCombo.builder()
                                    .menteeId(menteeEntity.getMenteeId())
                                    .mentorId(0L)
                                    .build())
                    );
                }
        );

        return combos;
    }
}
