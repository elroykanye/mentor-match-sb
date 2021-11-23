package com.kanyelings.telmah.mentormatchsb.config;

import com.kanyelings.telmah.mentormatchsb.data.entity.MentorEntity;

import java.util.List;

public class Constants {
    public static final MentorEntity DEFAULT_MENTOR_COME = MentorEntity.builder()
            .firstName("Elroy")
            .secondName("Kanye")
            .department("COME")
            .phoneNumber("672270627")
            .gender("M")
            .mentorId(-25L)
            .build();

    public static final List<MentorEntity> DEFAULT_MENTORS = List.of(
            DEFAULT_MENTOR_COME
    );

    public static final String KANYE_SECRET_KEY = "kanyelroy";
}