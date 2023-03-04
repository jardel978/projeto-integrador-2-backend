package com.dmh.msaccounts.model.dto;

import com.dmh.msaccounts.model.enums.CardsTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class TransactionDTO implements Serializable {

    private CardsTypeEnum cardType;
    private String accountId;
    private BigDecimal value;
    private Date dateTransaction;
    private String transactionType;
    private String description;

}
