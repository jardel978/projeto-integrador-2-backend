package com.dmh.msaccounts.model.dto;

import com.dmh.msaccounts.model.enums.CardsTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardsDTO implements Serializable {

    private String id;
    private String number;
    private String expirationDate;
    private String cvc;
    private String name;
    private BigDecimal ammount;
//    private boolean isExternal;
    private String accountId;
    private CardsTypeEnum cardType;
}
