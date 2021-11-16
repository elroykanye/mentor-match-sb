package com.kanyelings.telmah.mentormatchsb.business.service.impl;

import com.kanyelings.telmah.mentormatchsb.api.dto.MatchDto;
import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import com.kanyelings.telmah.mentormatchsb.api.dto.MentorDto;
import com.kanyelings.telmah.mentormatchsb.business.mapper.MenteeMapper;
import com.kanyelings.telmah.mentormatchsb.business.mapper.MentorMapper;
import com.kanyelings.telmah.mentormatchsb.business.model.MatchComboV2;
import com.kanyelings.telmah.mentormatchsb.business.model.MatchSize;
import com.kanyelings.telmah.mentormatchsb.business.service.MatchService;
import com.kanyelings.telmah.mentormatchsb.data.entity.MatchEntity;
import com.kanyelings.telmah.mentormatchsb.data.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.data.entity.MentorEntity;
import com.kanyelings.telmah.mentormatchsb.data.repository.MatchRepository;
import com.kanyelings.telmah.mentormatchsb.data.repository.MenteeRepository;
import com.kanyelings.telmah.mentormatchsb.data.repository.MentorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final MentorRepository mentorRepository;
    private final MenteeRepository menteeRepository;
    private final MentorMapper mentorMapper;
    private final MenteeMapper menteeMapper;

    @Override
    public ResponseEntity<String> shuffleMatches() {
        shuffleMatchesHelper();

        return new ResponseEntity<>("Shuffle successful", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<MatchDto>> getAllMatches(Boolean shuffle) {
        List<MatchDto> matchDtos = new ArrayList<>();
        if (matchRepository.findAll().isEmpty()) {
            // case for if the match repository is empty
            return new ResponseEntity<>(List.of(), HttpStatus.OK);
        } else {
            // if not is the case that the match repository is empty,
            // shuffle the matches first
            if (shuffle != null && shuffle) shuffleMatches();

            matchRepository.findAll()
                    .stream()
                    .map(MatchEntity::getMentorId)
                    .distinct()
                    .forEach(mentorId -> {
                        MentorDto mentorDto = mentorMapper.mapMentorEntityToDto(mentorRepository.getById(mentorId));

                        MatchDto matchDto = MatchDto.builder()
                                .mentor(mentorDto)
                                .mentees(matchRepository.findAllByMentorId(mentorId)
                                        .stream()
                                        .map(MatchEntity::getMenteeId)
                                        .map(menteeRepository::getById)
                                        .map(menteeMapper::mapMenteeEntityToDto)
                                        .collect(Collectors.toList()))
                                .build();
                        matchDtos.add(matchDto);
                    });

            /*
            mapMatchListToMentorMenteesMap(matchRepository.findAll()
                    .stream()
                    .map(MatchEntity::getMentorId)
                    .collect(Collectors.toList())
            ).forEach((mentorDto, menteeDtos) -> matchDtos.add(MatchDto.builder()
                            .mentor(mentorDto)
                            .mentees(menteeDtos)
                            .build())
            );
             */

            return new ResponseEntity<>(matchDtos, HttpStatus.OK);
        }
    }

    /*
    private Map<MentorDto, List<MenteeDto>> mapMatchListToMentorMenteesMap(List<Long> mentorIds) {
        Set<Long> uniqueMentorIds = new HashSet<>(mentorIds);

        Map<MentorDto, List<MenteeDto>> matchesMap = new HashMap<>();
        uniqueMentorIds.forEach(mentorId -> matchesMap.put(
                mentorMapper.mapMentorEntityToDto(mentorRepository.getById(mentorId)),
                matchRepository.findAllByMentorId(mentorId)
                        .stream()
                        .map(MatchEntity::getMenteeId)
                        .map(menteeRepository::getById)
                        .map(menteeMapper::mapMenteeEntityToDto)
                        .collect(Collectors.toList())
        ));

        return matchesMap;
    }
     */

    @Override
    public ResponseEntity<List<MenteeDto>> getAllMenteesByMentorId(Long mentorId) {
        return matchRepository.findAllByMentorId(mentorId).isEmpty() ?
                new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND):
                new ResponseEntity<>(
                        matchRepository.findAllByMentorId(mentorId)
                                .stream()
                                .map(MatchEntity::getMenteeId)
                                .map(this::mapMenteeIdToMenteeEntity)
                                .map(menteeMapper::mapMenteeEntityToDto)
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
                new ResponseEntity<>(
                        mentorMapper.mapMentorEntityToDto(mentor.get()),
                        HttpStatus.FOUND
                );
    }

    @Override
    public ResponseEntity<String> deleteAllMatches() {
        matchRepository.deleteAll();
        return new ResponseEntity<>("Matches deleted", HttpStatus.OK);
    }

    private MenteeEntity mapMenteeIdToMenteeEntity(Long menteeId) {
        return menteeRepository.findById(menteeId).orElseThrow();
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

    /**
    * @author Elroy Kanye
    */
    private Map<MentorEntity, List<MenteeEntity>> shuffleHelperV2(List<MentorEntity> mentors, List<MenteeEntity> mentees) {

        // create an empty hashmap for the matches
        Map<MentorEntity, List<MenteeEntity>> matches = new HashMap<>();

        // loop through the mentors list and place each mentor object as a key in the matches hashmap
        mentors.forEach(mentorEntity -> matches.put(mentorEntity, new ArrayList<>(0)));

        // create a sorted matches' hashmap in an atomic reference and assign the sortMatches() result to it
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



                    if (qualMentors.isEmpty()) {
                        qualMentors.add(sortedMatchCombos.get().stream().findFirst().orElseThrow().getMentor());
                    }

                    // convert the qualMentors list to an array of mentor entities
                    MentorEntity[] qm = qualMentors.toArray(new MentorEntity[0]);

                    // get the list of mentees from the sorted matches for the current mentor
                    // and add the current mentee to it
                    sortedMatches.get().get(qm[0]).add(menteeEntity); // add the mentee entity
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

        return List.of(matchSizesArray);
    }
}
