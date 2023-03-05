package com.dmh.msaccounts.model.dto;

import com.dmh.msaccounts.model.enums.CardsTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardsDTORequest implements Serializable {

    private String number;
    private String expirationDate;
    private String cvc;
    private String name;
    private BigDecimal ammount;
    private CardsTypeEnum cardType;

}
