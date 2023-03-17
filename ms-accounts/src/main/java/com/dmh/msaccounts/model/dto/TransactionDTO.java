package com.dmh.msaccounts.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO implements Serializable {

    @NotNull(message = "\"value\" is required.")
    private BigDecimal value;
    private Date dateTransaction;
    @NotNull(message = "\"transactionType\" is required.")
    private String transactionType;
    private String description;
    private Long accountOriginId;

}
