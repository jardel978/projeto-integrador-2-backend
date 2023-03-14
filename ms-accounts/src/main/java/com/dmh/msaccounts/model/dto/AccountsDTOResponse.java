package com.dmh.msaccounts.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountsDTOResponse implements Serializable {

    private String id;
    private String userId;
    private String account;
    private BigDecimal ammount;
    private Set<CardsDTO> cards;

}
