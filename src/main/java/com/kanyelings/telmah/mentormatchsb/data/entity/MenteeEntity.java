package com.kanyelings.telmah.mentormatchsb.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder @ToString
public class MenteeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long menteeId;

    private String firstName;

    private String secondName;

    private String phoneNumber;

    private String waNumber;

    private String department;

    private String gender;

    private String previousSchool;
}
