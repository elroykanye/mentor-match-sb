package com.kanyelings.telmah.mentormatchsb.business.service;

import com.kanyelings.telmah.mentormatchsb.api.dto.MenteeDto;

import java.util.List;

public interface MenteeService {
    List<MenteeDto> getAllMentees();

    void addNewMentee(MenteeDto newMentee);
}
