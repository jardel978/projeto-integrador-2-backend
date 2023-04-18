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
public class CardsDTO implements Serializable {

    private Long id;
    private String number;
    private String expirationDate;
    private String cvc;
    private String name;
    private BigDecimal ammount;
    private Long accountId;
    private CardsTypeEnum cardType;

}
