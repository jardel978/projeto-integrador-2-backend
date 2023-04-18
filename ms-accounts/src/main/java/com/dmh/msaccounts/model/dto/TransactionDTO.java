package com.dmh.msaccounts.model.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO implements Serializable {

    private Long id;
    @NotNull(message = "\"value\" is required.")
    private BigDecimal value;
    private Date dateTransaction;
    @NotNull(message = "\"transactionType\" is required.")
    private String transactionType;
    private String description;
    private Long accountOriginId;

}
