package com.dmh.msaccounts.model.dto;

import com.dmh.msaccounts.controller.AccountController;
import com.dmh.msaccounts.model.Accounts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TransferenceDTO extends TransactionDTO implements Serializable {

    @NotNull(message = "Account ID is required")
    private Long accountId;

}
