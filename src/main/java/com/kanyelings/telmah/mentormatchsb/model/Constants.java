package com.kanyelings.telmah.mentormatchsb.model;

import com.kanyelings.telmah.mentormatchsb.entity.MentorEntity;

public class Constants {
    public static final MentorEntity DEFAULT_MENTOR = MentorEntity.builder()
            .firstName("Elroy")
            .secondName("Kanye")
            .department("COME")
            .phoneNumber("672270627")
            .gender("M")
            .mentorId(-25L)
            .build();

}
