package com.kanyelings.telmah.mentormatchsb.model;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchCombo {
    private Long mentorId;
    private Long menteeId;
}
