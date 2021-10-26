package com.kanyelings.telmah.mentormatchsb.business.service.impl;

import com.kanyelings.telmah.mentormatchsb.business.service.MentorService;
import com.kanyelings.telmah.mentormatchsb.data.entity.MentorEntity;
import com.kanyelings.telmah.mentormatchsb.data.repository.MentorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;

    @Override
    public List<MentorEntity> getAllMentors() {
        return mentorRepository.findAll();
    }

    @Override
    public void addNewMentor(MentorEntity newMentor) {
        mentorRepository.save(newMentor);
    }
}
