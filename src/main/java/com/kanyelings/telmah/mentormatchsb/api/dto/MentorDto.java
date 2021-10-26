package com.kanyelings.telmah.mentormatchsb.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder @ToString
public class MentorDto {
    @JsonProperty("mentee_id")
    private Long mentorId;

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

    @JsonProperty(value = "gender")
    private String gender;
}
