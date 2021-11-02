package com.kanyelings.telmah.mentormatchsb.data.entity;

import com.kanyelings.telmah.mentormatchsb.data.entity.common.MentorMenteeCommon;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenteeEntity extends MentorMenteeCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long menteeId;


    private String previousSchool;
}
