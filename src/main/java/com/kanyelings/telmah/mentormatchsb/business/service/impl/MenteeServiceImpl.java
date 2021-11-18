package com.kanyelings.telmah.mentormatchsb.business.service.impl;

import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import com.kanyelings.telmah.mentormatchsb.business.mapper.MenteeMapper;
import com.kanyelings.telmah.mentormatchsb.business.service.MenteeService;
import com.kanyelings.telmah.mentormatchsb.business.util.ImageUtil;
import com.kanyelings.telmah.mentormatchsb.data.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.data.repository.MenteeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<String> addNewMentee(MenteeDto newMentee, MultipartFile imageFile) {
        boolean saved = false;
        try {
            String imagePath = ImageUtil.saveUserImage(newMentee.getImage(), newMentee.getUsername());
            MenteeEntity menteeEntity = menteeMapper.mapDtoToMentorEntity(newMentee);
            menteeEntity.setImagePath(imagePath);
            menteeRepository.save(menteeEntity);
            saved = true;
        } catch (Exception ignored){
        }
        return saved ?
                new ResponseEntity<>("Mentee added", HttpStatus.CREATED):
                new ResponseEntity<>("Mentee not added", HttpStatus.CONFLICT);
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
                new ResponseEntity<>(menteeDtoRef, HttpStatus.OK):
                new ResponseEntity<>("Mentee does not exist", HttpStatus.NOT_FOUND);
    }
}
