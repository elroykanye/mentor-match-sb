package com.kanyelings.telmah.mentormatchsb.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder @ToString
public class MentorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mentorId;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "second_name")
    private String secondName;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "waNumber")
    private String waNumber;

    @JsonProperty(value = "department")
    private String department;

    @JsonProperty(value = "gender")
    private String gender;
}
