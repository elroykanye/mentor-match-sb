package com.kanyelings.telmah.mentormatchsb.data.entity.common;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class MentorMenteeCommon implements Serializable {
    @Column(length = 32, nullable = false, unique = true)
    private String username;

    @Column(length = 64, unique = true)
    private String email;

    @Column(length = 64)
    private String firstName;

    @Column(length = 64)
    private String secondName;

    @Column(length = 32)
    private String phoneNumber;

    @Column(length = 32)
    private String waNumber;

    @Column(length = 8)
    private String department;

    @Column(length = 8)
    private String level;

    @Column(length = 8)
    private String gender;

    @Column(length = 512)
    private String about;
}
