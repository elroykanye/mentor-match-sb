package com.kanyelings.telmah.mentormatchsb.business.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.kanyelings.telmah.mentormatchsb.business.model.enums.Role;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntitySearch {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "role")
    private Role role;

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
