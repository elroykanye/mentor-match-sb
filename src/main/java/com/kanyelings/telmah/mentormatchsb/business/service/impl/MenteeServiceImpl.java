package com.kanyelings.telmah.mentormatchsb.service.impl;

import com.kanyelings.telmah.mentormatchsb.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.repository.MenteeRepository;
import com.kanyelings.telmah.mentormatchsb.service.MenteeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenteeServiceImpl implements MenteeService {

    private final MenteeRepository menteeRepository;

    @Override
    public List<MenteeEntity> getAllMentees() {
        return menteeRepository.findAll();
    }

    @Override
    public void addNewMentee(MenteeEntity newMentee) {
        menteeRepository.save(newMentee);
    }
}
