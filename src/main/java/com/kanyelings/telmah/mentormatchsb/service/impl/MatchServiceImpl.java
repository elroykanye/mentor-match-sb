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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final MentorRepository mentorRepository;
    private final MenteeRepository menteeRepository;

    @Override
    public ResponseEntity<String> shuffleMatches() {
        // response entity message
        String message = "Shuffle successful";
        HttpStatus status = HttpStatus.OK;

        shuffleMatchesHelper();

        return new ResponseEntity<>(message, status);
    }

    @Override
    public ResponseEntity<List<Map<MentorEntity, MenteeEntity>>> getAllMatches() {
        shuffleMatchesHelper();

        return matchRepository.findAll().isEmpty() ?
                new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND):
                new ResponseEntity<>(
                        matchRepository.findAll()
                                .stream()
                                .map(this::mapMatchToMentorMenteesMap)
                                .collect(Collectors.toList())
                        , HttpStatus.FOUND
                );

    }

    @Override
    public ResponseEntity<List<MenteeEntity>> getAllMenteesByMentorId(Long mentorId) {
        return matchRepository.findAllByMentorId(mentorId).isEmpty() ?
                new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND):
                new ResponseEntity<>(
                        matchRepository.findAllByMentorId(mentorId)
                                .stream()
                                .map(MatchEntity::getMenteeId)
                                .map(this::mapMenteeIdToMenteeEntity)
                                .collect(Collectors.toList()),
                        HttpStatus.FOUND
                );
    }

    @Override
    public ResponseEntity<?> getMentorByMenteeId(Long menteeId) {
        AtomicReference<MentorEntity> mentor = new AtomicReference<>();

        matchRepository.findByMenteeId(menteeId).ifPresentOrElse(
                matchEntity -> mentorRepository.findById(matchEntity.getMentorId()).ifPresentOrElse(
                        mentor::set,
                        () -> {}
                ),
                () -> {}
        );
        return mentor.get() == null ?
                new ResponseEntity<>("Mentor does not exists", HttpStatus.NOT_FOUND):
                new ResponseEntity<>(mentor.get(), HttpStatus.FOUND);
    }

    private MenteeEntity mapMenteeIdToMenteeEntity(Long menteeId) {
        return menteeRepository.findById(menteeId).orElseThrow();
    }

    private Map<MentorEntity, MenteeEntity> mapMatchToMentorMenteesMap(MatchEntity matchEntity) {
        return Map.of(
                mentorRepository.getById(matchEntity.getMentorId()),
                menteeRepository.getById(matchEntity.getMenteeId())
        );
    }

    private void shuffleMatchesHelper() {

        if ( !matchRepository.findAll().isEmpty() ) {
            // if matches already exist, all should be deleted
            matchRepository.deleteAll();
        }

        // get a list of all mentors and mentees
        List<MenteeEntity> mentees = menteeRepository.findAll();
        List<MentorEntity> mentors = mentorRepository.findAll();

        // create a combo match map for mentor to mentee(s) using the shuffle helper v2
        Map<MentorEntity, List<MenteeEntity>> combMatches = shuffleHelperV2(mentors, mentees);

        // loop through the combo matches via list.forEach()
        combMatches.forEach(
                (mentorEntity, menteeEntities) -> {
                    // for the current mentor, create a match entity and persist
                    menteeEntities.forEach(menteeEntity -> {
                        MatchEntity matchEntity = MatchEntity.builder()
                                .mentorId(mentorEntity.getMentorId())
                                .menteeId(menteeEntity.getMenteeId())
                                .build(); // builder for the match entity object

                        matchRepository.save(matchEntity); // save the created match entity
                    });
                }
        );
    }

    // obsolete shuffle helper -  do not deleted
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

    /**
    * @author Elroy Kanye
    */
    private Map<MentorEntity, List<MenteeEntity>> shuffleHelperV2(List<MentorEntity> mentors, List<MenteeEntity> mentees) {
        // build the default mentor
        MentorEntity defaultElroy = Constants.DEFAULT_MENTOR_COME;

        // create an empty hashmap for the matches
        Map<MentorEntity, List<MenteeEntity>> matches = new HashMap<>();

        // loop through the mentors list and place each mentor object as a key in the matches hashmap
        mentors.forEach(mentorEntity -> matches.put(mentorEntity, new ArrayList<>(0)));

        // add the default mentor in the matches hashmap
        matches.put(defaultElroy, new ArrayList<>(0));

        // create a sorted matches hashmap in an atomic reference and assign the sortMatches() result to it
        AtomicReference<List<MatchComboV2>> sortedMatchCombos = new AtomicReference<>();
        AtomicReference<Map<MentorEntity, List<MenteeEntity>>> sortedMatches = new AtomicReference<>();
        
        // set the sortedMatches to originally be all matches existing
        sortedMatches.set(matches);


        mentees.forEach(
            // loop through the mentees list
                menteeEntity -> {
                    // call the sort matches function to work on the sortedMatches map
                    // the map is converted into a list of MatchComboV2 objects
                    sortedMatchCombos.set(sortMatches(sortedMatches.get()));
                    
                    // map the list of MatchComboV2 objects to a map of Mentor to MenteeList objects
                    mapMatchComboToMatchesMap(sortedMatchCombos.get(), sortedMatches.get());
                    
                    // sortedMatches.set(sortMatches(sortedMatches.get()));

                    // create an empty list of qualified mentors for the current mentee object
                    List<MentorEntity> qualMentors = new ArrayList<>();
                    
                    // stream (haha) through the sortedMatchesCombos list to find the most appropriate mentor
                    sortedMatchCombos.get()
                            .stream()
                            .filter(matchComboV2 -> {
                                // filtering process for the mentor and mentee match
                                MentorEntity mentorEntity = matchComboV2.getMentor();
                                return mentorEntity.getDepartment().equals(menteeEntity.getDepartment()) &&
                                        mentorEntity.getGender().equals(menteeEntity.getGender());
                            }) // use the forEach to add the found mentors to the qualified list
                            .forEach(matchComboV2 -> qualMentors.add(matchComboV2.getMentor()));


                    // convert the qualMentors list to an array of mentor entities
                    MentorEntity[] qm = qualMentors.toArray(new MentorEntity[0]);
                    // TODO explain this
                    // get the list of mentees from the sorted matches for the current mentor
                    // and add the current mentee to it
                    sortedMatches.get().get(
                        // if qm is less than 1, get the default mentor, else get the first element in qm
                            qm.length < 1 ? Constants.DEFAULT_MENTOR_COME : qm[0]
                    ).add(menteeEntity); // add the mentee entity
                }
        );


        return sortedMatches.get();
    }

    private void mapMatchComboToMatchesMap(List<MatchComboV2> matchComboV2s, Map<MentorEntity, List<MenteeEntity>> mentorEntityListMap) {
        // mapper for MathcComboV2 objects to MentorMenteeList map
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
