package com.kanyelings.telmah.mentormatchsb.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
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

    @JsonProperty(value = "department")
    private String department;

    @JsonProperty(value = "gender")
    private String gender;

    @JsonProperty (value = "previousSchool")
    private String previousSchool;
}
