package com.kanyelings.telmah.mentormatchsb.business.model;

import com.kanyelings.telmah.mentormatchsb.data.entity.MenteeEntity;
import com.kanyelings.telmah.mentormatchsb.data.entity.MentorEntity;
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
