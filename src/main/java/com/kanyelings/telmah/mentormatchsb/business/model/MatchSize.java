package com.kanyelings.telmah.mentormatchsb.business.model;

import com.kanyelings.telmah.mentormatchsb.data.entity.MentorEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class MatchSize {
    private MentorEntity mentor;
    private Integer size;
}
