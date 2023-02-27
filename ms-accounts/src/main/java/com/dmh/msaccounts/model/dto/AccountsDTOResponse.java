package com.dmh.msaccounts.model.dto;

import com.dmh.msaccounts.model.Cards;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountsDTOResponse  implements Serializable {

    private String id;
    private String account;
    private String userId;
    private Set<Cards> cards;

}
