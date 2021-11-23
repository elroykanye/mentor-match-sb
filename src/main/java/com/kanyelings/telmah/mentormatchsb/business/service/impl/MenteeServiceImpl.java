package com.kanyelings.telmah.mentormatchsb.business.service.impl;

import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import com.kanyelings.telmah.mentormatchsb.business.mapper.MenteeMapper;
import com.kanyelings.telmah.mentormatchsb.business.service.MenteeService;
import com.kanyelings.telmah.mentormatchsb.business.util.SecretUtil;
import com.kanyelings.telmah.mentormatchsb.data.repository.MenteeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenteeServiceImpl implements MenteeService {
    private final MenteeMapper menteeMapper;
    private final MenteeRepository menteeRepository;

    @Override
    public ResponseEntity<List<MenteeDto>> getAllMentees() {
        List<MenteeDto> mentees = menteeRepository.findAll()
                .stream()
                .map(menteeMapper::mapMenteeEntityToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mentees, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> addNewMentee(MenteeDto newMentee) {
        if(!menteeRepository.findByUsernameOrEmail(newMentee.getUsername(), newMentee.getEmail()).isPresent()) {
            menteeRepository.save(menteeMapper.mapDtoToMentorEntity(newMentee));
            return new ResponseEntity<>("Mentee added", HttpStatus.CREATED);
        } return new ResponseEntity<>("Already exists", HttpStatus.CONFLICT);
    }

    @Override
    public ResponseEntity<?> getMenteeById(Long menteeId) {
        AtomicReference<MenteeDto> menteeDtoRef = new AtomicReference<>();
        AtomicBoolean found = new AtomicBoolean(true);
        menteeRepository.findById(menteeId).ifPresentOrElse(
                mentorEntity -> menteeDtoRef.set(menteeMapper.mapMenteeEntityToDto(mentorEntity)),
                () -> found.set(false)
        );
        return found.get() ?
                ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(menteeDtoRef.get()):
                new ResponseEntity<>("Mentee does not exist", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> deleteAllMentees(String secret) {
        if (SecretUtil.validSecret(secret)) {
            menteeRepository.deleteAll();
            return new ResponseEntity<>("Mentees deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Mentees not deleted", HttpStatus.FORBIDDEN);
    }
}
