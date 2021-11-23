package com.kanyelings.telmah.mentormatchsb.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder @ToString
public class MentorDto {
    @JsonProperty("id")
    private Long mentorId;

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "second_name")
    private String secondName;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "wa_number")
    private String waNumber;

    @JsonProperty(value = "department")
    private String department;

    @JsonProperty(value = "level")
    private String level;

    @JsonProperty(value = "gender")
    private String gender;

    @JsonProperty(value = "about")
    private String about;
}
