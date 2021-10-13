package com.kanyelings.telmah.mentormatchsb.service.impl;

import com.kanyelings.telmah.mentormatchsb.entity.MatchEntity;
import com.kanyelings.telmah.mentormatchsb.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.entity.MentorEntity;
import com.kanyelings.telmah.mentormatchsb.model.MatchCombo;
import com.kanyelings.telmah.mentormatchsb.model.MatchSize;
import com.kanyelings.telmah.mentormatchsb.repository.MatchRepository;
import com.kanyelings.telmah.mentormatchsb.repository.MenteeRepository;
import com.kanyelings.telmah.mentormatchsb.repository.MentorRepository;
import com.kanyelings.telmah.mentormatchsb.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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

        if ( !matchRepository.findAll().isEmpty() ) {
            matchRepository.deleteAll();
        }

        List<MenteeEntity> mentees = menteeRepository.findAll();
        List<MentorEntity> mentors = mentorRepository.findAll();

        List<MatchCombo> matchCombos = shuffleHelper(mentors, mentees);

        matchCombos.forEach(matchCombo -> matchRepository.save(
                MatchEntity.builder()
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
                            .filter(
                                    mentorEntity ->
                                            Objects.equals(menteeEntity.getGender(), mentorEntity.getGender()) &&
                                                    Objects.equals(menteeEntity.getDepartment(), mentorEntity.getDepartment())
                            )
                            .findFirst();
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

    private Map<MentorEntity, List<MenteeEntity>> shuffleHelperV2(List<MentorEntity> mentors, List<MenteeEntity> mentees) {
        MentorEntity defaultElroy = MentorEntity.builder()
                .firstName("Elroy")
                .secondName("Kanye")
                .department("COME")
                .phoneNumber("672270627")
                .mentorId(-25L)
                .build();

        Map<MentorEntity, List<MenteeEntity>> matches = new HashMap<>();

        mentors.forEach(mentorEntity -> matches.put(mentorEntity, new ArrayList<>(0)));
        matches.put(defaultElroy, new ArrayList<>(0));

        AtomicReference<Map<MentorEntity, List<MenteeEntity>>> sortedMatches = new AtomicReference<>(sortMatches(matches));

        mentees.forEach(
                menteeEntity -> {
                    sortedMatches.set(sortMatches(sortedMatches.get()));

                    MentorEntity mentor = sortedMatches.get().keySet().stream().filter(
                            mentorEntity -> mentorEntity.getDepartment().equals(menteeEntity.getDepartment()) &&
                            mentorEntity.getGender().equals(menteeEntity.getGender()))
                            .findFirst()
                            .orElse(defaultElroy);

                    sortedMatches.get().get(mentor);
                }
        );


        return sortedMatches.get();
    }

    private Map<MentorEntity, List<MenteeEntity>> sortMatches(Map<MentorEntity, List<MenteeEntity>> matches) {
        Map<MentorEntity, Integer> matchSizes = new HashMap<>();
        matches.forEach((mentorEntity, menteeEntities) -> matchSizes.put(mentorEntity, menteeEntities.size()));

        Map<MentorEntity, List<MenteeEntity>> sortedMatches = new HashMap<>();

        List<MatchSize> sortedMatchSizes = sortMatchSizes(matchSizes);

        sortedMatchSizes.forEach(
                matchSize -> sortedMatches.put(matchSize.getMentor(), matches.get(matchSize.getMentor()))
        );


        return sortedMatches;
    }

    private List<MatchSize> sortMatchSizes(Map<MentorEntity, Integer> matchSizes) {
        List<MatchSize> matchSizeList = new ArrayList<>();
        matchSizes.forEach((mentorEntity, integer) -> matchSizeList.add(
                MatchSize.builder().mentor(mentorEntity).size(integer).build()
        ));

        matchSizeList.sort((o1, o2) -> o2.getSize().compareTo(o1.getSize()));

        return matchSizeList;
    }


}
