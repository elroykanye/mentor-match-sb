package com.kanyelings.telmah.mentormatchsb.service;

import com.kanyelings.telmah.mentormatchsb.entity.MentorEntity;

import java.util.List;

public interface MentorService {
    List<MentorEntity> getAllMentors();

    void addNewMentor(MentorEntity newMentor);
}
