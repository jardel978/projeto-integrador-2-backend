package com.dmh.msaccounts.model.dto.requests;

import com.dmh.msaccounts.model.enums.CardsTypeEnum;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
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
