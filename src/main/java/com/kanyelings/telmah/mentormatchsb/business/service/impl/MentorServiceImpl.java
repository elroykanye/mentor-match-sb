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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MentorServiceImpl implements MentorService {
    private final MentorMapper mentorMapper;
    private final MentorRepository mentorRepository;

    @Override
    public List<MentorDto> getAllMentors() {
        return mentorRepository.findAll()
                .stream()
                .map(mentorMapper::mapMentorEntityToDto)
                .collect(Collectors.toList());
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
}
