package com.kanyelings.telmah.mentormatchsb.data.entity;

import com.kanyelings.telmah.mentormatchsb.data.entity.common.MentorMenteeCommon;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder @ToString
public class MenteeEntity extends MentorMenteeCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long menteeId;


    private String previousSchool;
}
