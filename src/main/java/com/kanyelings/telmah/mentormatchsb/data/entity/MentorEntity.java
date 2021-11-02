package com.kanyelings.telmah.mentormatchsb.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class MentorEntity extends MentorMenteeCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mentorId;


}
