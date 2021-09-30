package com.kanyelings.telmah.mentormatchsb.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Data @Builder
public class MenteeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long menteeId;

    @JsonProperty(value = "firstName")
    private String firstName;

    @JsonProperty(value = "secondName")
    private String secondName;

    @JsonProperty(value = "phoneNumber")
    private String phoneNumber;

    @JsonProperty(value = "gender")
    private String gender;

    @JsonProperty (value = "previousSchool")
    private String previousSchool;
}
