package com.kanyelings.telmah.mentormatchsb.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kanyelings.telmah.mentormatchsb.data.entity.common.MentorMenteeCommon;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder @ToString
public class MentorEntity extends MentorMenteeCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mentorId;


}
