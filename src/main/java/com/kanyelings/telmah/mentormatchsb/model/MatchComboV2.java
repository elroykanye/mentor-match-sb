package com.kanyelings.telmah.mentormatchsb.model;

import com.kanyelings.telmah.mentormatchsb.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.entity.MentorEntity;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchComboV2 {
    private MentorEntity mentor;
    private List<MenteeEntity> mentees;
}
