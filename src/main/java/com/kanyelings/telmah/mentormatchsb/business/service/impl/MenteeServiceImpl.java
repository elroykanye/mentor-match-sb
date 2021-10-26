package com.kanyelings.telmah.mentormatchsb.business.service.impl;

import com.kanyelings.telmah.mentormatchsb.data.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.data.repository.MenteeRepository;
import com.kanyelings.telmah.mentormatchsb.business.service.MenteeService;
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
