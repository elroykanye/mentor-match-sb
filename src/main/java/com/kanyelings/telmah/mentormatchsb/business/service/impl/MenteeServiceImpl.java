package com.kanyelings.telmah.mentormatchsb.business.service.impl;

import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;
import com.kanyelings.telmah.mentormatchsb.business.mapper.MenteeMapper;
import com.kanyelings.telmah.mentormatchsb.data.repository.MenteeRepository;
import com.kanyelings.telmah.mentormatchsb.business.service.MenteeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenteeServiceImpl implements MenteeService {
    private final MenteeMapper menteeMapper;
    private final MenteeRepository menteeRepository;

    @Override
    public List<MenteeDto> getAllMentees() {
        return menteeRepository.findAll()
                .stream()
                .map(menteeMapper::mapMenteeEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addNewMentee(MenteeDto newMentee) {
        menteeRepository.save(menteeMapper.mapDtoToMentorEntity(newMentee));
    }
}
