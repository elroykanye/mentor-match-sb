package com.kanyelings.telmah.mentormatchsb.business.service;

import com.kanyelings.telmah.mentormatchsb.api.dto.MentorDto;

import java.util.List;

public interface MentorService {
    List<MentorDto> getAllMentors();

    void addNewMentor(MentorDto newMentor);
}
