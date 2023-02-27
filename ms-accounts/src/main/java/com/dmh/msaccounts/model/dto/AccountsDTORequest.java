package com.dmh.msaccounts.model.dto;

import com.dmh.msaccounts.model.Cards;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountsDTORequest implements Serializable {

    private String account;
    @NotNull(message = "\"UserId\" is required.")
    private String userId;
    private Set<Cards> cards;



}
