package com.kanyelings.telmah.mentormatchsb.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchDto {
    @JsonProperty("mentor")
    private MentorDto mentor;

    @JsonProperty("mentees")
    private List<MenteeDto> mentees;
}
