package com.kanyelings.telmah.mentormatchsb.model;

import com.kanyelings.telmah.mentormatchsb.entity.MentorEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class MatchSize {
    private MentorEntity mentor;
    private Integer size;
}
