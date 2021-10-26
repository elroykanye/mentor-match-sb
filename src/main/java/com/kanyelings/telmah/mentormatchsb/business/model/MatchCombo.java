package com.kanyelings.telmah.mentormatchsb.business.model;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchCombo {
    private Long mentorId;
    private Long menteeId;
}
