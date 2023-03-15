package com.dmh.msaccounts.model.dto;

import com.dmh.msaccounts.model.enums.CardsTypeEnum;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class TransactionDTO implements Serializable {

    @NotNull(message = "Card Type is required.")
    private CardsTypeEnum cardType;
    private Long accountId;
    @NotNull(message = "Value is required.")
    private BigDecimal value;
    private Date dateTransaction;
    private String transactionType;
    private String description;

}
