package com.kanyelings.telmah.mentormatchsb.business.service.impl;

import com.kanyelings.telmah.mentormatchsb.api.dto.MentorDto;
import com.kanyelings.telmah.mentormatchsb.business.mapper.MentorMapper;
import com.kanyelings.telmah.mentormatchsb.business.service.MentorService;
import com.kanyelings.telmah.mentormatchsb.business.util.ImageUtil;
import com.kanyelings.telmah.mentormatchsb.data.entity.MentorEntity;
import com.kanyelings.telmah.mentormatchsb.data.repository.MentorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MentorServiceImpl implements MentorService {
    private final MentorMapper mentorMapper;
    private final MentorRepository mentorRepository;

    @Override
    public ResponseEntity<List<MentorDto>> getAllMentors() {
        List<MentorDto> mentors = mentorRepository.findAll()
                .stream()
                .map(mentorMapper::mapMentorEntityToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mentors, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addNewMentor(MentorDto newMentor) {
        mentorRepository.save(mentorMapper.mapDtoToMentorEntity(newMentor));

        boolean saved = false;
        try {
            String imagePath = ImageUtil.saveUserImage(newMentor.getImage(), newMentor.getUsername());
            MentorEntity mentorEntity = mentorMapper.mapDtoToMentorEntity(newMentor);
            mentorEntity.setImagePath(imagePath);
            mentorRepository.save(mentorEntity);
            saved = true;
        } catch (Exception ignored){
        }

        return saved ?
                new ResponseEntity<>("Mentee added", HttpStatus.CREATED):
                new ResponseEntity<>("Mentee not added", HttpStatus.CONFLICT);
    }

    @Override
    public ResponseEntity<?> getMentorById(Long mentorId) {
        AtomicReference<MentorDto> mentorDtoRef = new AtomicReference<>();
        AtomicBoolean found = new AtomicBoolean(true);
        mentorRepository.findById(mentorId).ifPresentOrElse(
                mentorEntity -> mentorDtoRef.set(mentorMapper.mapMentorEntityToDto(mentorEntity)),
                () -> found.set(false)
        );
        return found.get() ?
                new ResponseEntity<>(mentorDtoRef, HttpStatus.FOUND):
                new ResponseEntity<>("Mentor does not exist", HttpStatus.NOT_FOUND);
    }
}
