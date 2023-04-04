package com.dmh.msaccounts.model.dto;

import com.dmh.msaccounts.model.enums.CardsTypeEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DepositDTO extends TransactionDTO implements Serializable {

    @NotNull(message = "\"cardType\" is required.")
    private CardsTypeEnum cardType;
    private Long cardId;
    private String cardNumber;

}
