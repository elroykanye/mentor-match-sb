package com.kanyelings.telmah.mentormatchsb.service;

import com.kanyelings.telmah.mentormatchsb.entity.MenteeEntity;

import java.util.List;

public interface MenteeService {
    List<MenteeEntity> getAllMentees();

    void addNewMentee(MenteeEntity newMentee);
}
