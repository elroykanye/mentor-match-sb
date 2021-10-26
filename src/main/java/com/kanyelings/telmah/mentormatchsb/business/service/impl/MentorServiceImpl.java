package com.kanyelings.telmah.mentormatchsb.business.service.impl;

import com.kanyelings.telmah.mentormatchsb.api.dto.MentorDto;
import com.kanyelings.telmah.mentormatchsb.business.mapper.MentorMapper;
import com.kanyelings.telmah.mentormatchsb.business.service.MentorService;
import com.kanyelings.telmah.mentormatchsb.data.repository.MentorRepository;
import lombok.AllArgsConstructor;
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
    public void addNewMentor(MentorDto newMentor) {
        mentorRepository.save(mentorMapper.mapDtoToMentorEntity(newMentor));
    }
}
