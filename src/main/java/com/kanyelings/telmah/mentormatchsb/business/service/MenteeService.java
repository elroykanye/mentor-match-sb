package com.kanyelings.telmah.mentormatchsb.business.service;

import com.kanyelings.telmah.mentormatchsb.data.entity.MenteeEntity;

import java.util.List;

public interface MenteeService {
    List<MenteeEntity> getAllMentees();

    void addNewMentee(MenteeEntity newMentee);
}
