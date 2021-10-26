package com.kanyelings.telmah.mentormatchsb.business.service;

import com.kanyelings.telmah.mentormatchsb.data.entity.MentorEntity;

import java.util.List;

public interface MentorService {
    List<MentorEntity> getAllMentors();

    void addNewMentor(MentorEntity newMentor);
}
