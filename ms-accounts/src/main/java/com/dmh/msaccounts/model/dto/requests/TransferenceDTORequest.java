package com.dmh.msaccounts.model.dto.requests;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferenceDTORequest {

    private BigDecimal value;
    private String description;
    private Long accountDestinyId;

}
