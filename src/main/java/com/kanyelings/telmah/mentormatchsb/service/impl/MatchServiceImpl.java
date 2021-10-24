package com.kanyelings.telmah.mentormatchsb.service.impl;

import com.kanyelings.telmah.mentormatchsb.entity.MatchEntity;
import com.kanyelings.telmah.mentormatchsb.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.entity.MentorEntity;
import com.kanyelings.telmah.mentormatchsb.model.Constants;
import com.kanyelings.telmah.mentormatchsb.model.MatchCombo;
import com.kanyelings.telmah.mentormatchsb.model.MatchComboV2;
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

        // List<MatchCombo> matchCombos = shuffleHelper(mentors, mentees);
        /*
        matchCombos.forEach(matchCombo -> matchRepository.save(
                MatchEntity.builder()
                        .menteeId(matchCombo.getMenteeId())
                        .mentorId(matchCombo.getMentorId())
                        .build()
        ));
         */
        Map<MentorEntity, List<MenteeEntity>> combMatches = shuffleHelperV2(mentors, mentees);

        combMatches.forEach(
                (mentorEntity, menteeEntities) -> {
                    System.out.println("Saving info for : " + mentorEntity.getFirstName());
                    System.out.println(combMatches.get(mentorEntity).size());
                    menteeEntities.forEach(menteeEntity -> {
                        MatchEntity matchEntity = MatchEntity.builder()
                                .mentorId(mentorEntity.getMentorId())
                                .menteeId(menteeEntity.getMenteeId())
                                .build();
                        System.out.println(mentorEntity.getMentorId() + " : " + menteeEntity.getMenteeId());
                        matchRepository.save(matchEntity);
                    });
                }
        );
        return new ResponseEntity<>(message, status);
    }

    @Override
    public ResponseEntity<List<MatchEntity>> getAllMatches() {
        return null;
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
        // build the default mentor
        MentorEntity defaultElroy = Constants.DEFAULT_MENTOR_COME;

        // create an empty hashmap for the matches
        Map<MentorEntity, List<MenteeEntity>> matches = new HashMap<>();

        // loop through the mentors list and place each mentor object as a key in
        // the matches hashmap
        mentors.forEach(mentorEntity -> matches.put(mentorEntity, new ArrayList<>(0)));

        // add the default mentor in the matches hashmap
        matches.put(defaultElroy, new ArrayList<>(0));

        // create a sorted matches hashmap in an atomic reference and assign the sortMatches() result to it
        AtomicReference<List<MatchComboV2>> sortedMatchCombos = new AtomicReference<>();
        AtomicReference<Map<MentorEntity, List<MenteeEntity>>> sortedMatches = new AtomicReference<>();
        sortedMatches.set(matches);


        mentees.forEach(
                menteeEntity -> {
                    sortedMatchCombos.set(sortMatches(sortedMatches.get()));
                    mapMatchComboToMatchesMap(sortedMatchCombos.get(), sortedMatches.get());
                    // sortedMatches.set(sortMatches(sortedMatches.get()));
                    /*
                    List<MentorEntity> qualMentors = sortedMatches.get()
                            .keySet()
                            .stream()
                            .filter(mentorEntity -> mentorEntity.getDepartment().equals(menteeEntity.getDepartment()) &&
                                    mentorEntity.getGender().equals(menteeEntity.getGender()))
                            .collect(Collectors.toList());
                     */

                    List<MentorEntity> qualMentors = new ArrayList<>();
                    sortedMatchCombos.get()
                            .stream()
                            .filter(matchComboV2 -> {
                                MentorEntity mentorEntity = matchComboV2.getMentor();
                                return mentorEntity.getDepartment().equals(menteeEntity.getDepartment()) &&
                                        mentorEntity.getGender().equals(menteeEntity.getGender());
                            })
                            .forEach(matchComboV2 -> qualMentors.add(matchComboV2.getMentor()));


                    MentorEntity[] qm = qualMentors.toArray(new MentorEntity[0]);
                    // TODO explain this
                    sortedMatches.get().get(
                            qm.length < 1 ? Constants.DEFAULT_MENTOR_COME : qm[0]
                    ).add(menteeEntity);
                }
        );


        return sortedMatches.get();
    }

    private void mapMatchComboToMatchesMap(List<MatchComboV2> matchComboV2s, Map<MentorEntity, List<MenteeEntity>> mentorEntityListMap) {
        matchComboV2s.forEach(
                matchComboV2 -> mentorEntityListMap.put(matchComboV2.getMentor(), matchComboV2.getMentees())
        );
    }


    private List<MatchComboV2> sortMatches(Map<MentorEntity, List<MenteeEntity>> matches) {
        Map<MentorEntity, Integer> matchSizes = new HashMap<>();
        matches.forEach((mentorEntity, menteeEntities) -> matchSizes.put(mentorEntity, menteeEntities.size()));

        Map<MentorEntity, List<MenteeEntity>> sortedMatches = new HashMap<>();

        List<MatchSize> sortedMatchSizes = sortMatchSizes(matchSizes);

        sortedMatchSizes.forEach(
                matchSize -> sortedMatches.put(matchSize.getMentor(), matches.get(matchSize.getMentor()))
        );

        List<MatchComboV2> matchCombinations = new ArrayList<>();
        sortedMatchSizes.forEach(
                matchSize -> matchCombinations.add(
                        MatchComboV2.builder()
                        .mentor(matchSize.getMentor())
                        .mentees(matches.get(matchSize.getMentor()))
                        .build()
                )
        );



        System.out.println("Sorted\n\n");
        sortedMatches.forEach(
                (mentorEntity, menteeEntities) -> System.out.println(mentorEntity + " : " + menteeEntities.size())
        );
        System.out.println("\n\nSorted\n\n");


        return matchCombinations;
    }

    /**
     *
     * @param matchSizes
     * @return
     */
    private List<MatchSize> sortMatchSizes(Map<MentorEntity, Integer> matchSizes) {
        List<MatchSize> matchSizeList = new ArrayList<>();
        matchSizes.forEach((mentorEntity, integer) -> matchSizeList.add(
                MatchSize.builder().mentor(mentorEntity).size(integer).build()
        ));

        MatchSize[] matchSizesArray = matchSizeList.toArray(new MatchSize[0]);
        int n = matchSizesArray.length;

        // bubble sort
        for (int i = 0; i < n - 1 ; i++) {
            for (int j = i; j < n; j++) {
                if (matchSizesArray[i].getSize() > matchSizesArray[j].getSize()) {
                    MatchSize tempMS = matchSizesArray[i];
                    matchSizesArray[i] = matchSizesArray[j];
                    matchSizesArray[j] = tempMS;
                }
            }
        }

        /*
        System.out.println("Sorted\n\n");
        List.of(matchSizesArray).forEach(
                matchSize -> System.out.println(matchSize.getMentor() + " : " + matchSize.getSize())
        );
        System.out.println("\n\nSorted\n\n");

         */
        // matchSizeList.sort((o2, o1) -> o1.getSize().compareTo(o2.getSize()));

        return List.of(matchSizesArray);
    }


}
