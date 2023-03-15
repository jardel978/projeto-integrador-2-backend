package com.dmh.msaccounts.model.dto;

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
public class AccountsPatchDTORequest implements Serializable {

    //    private String userId;
    private Long id;
    private Set<CardsDTO> cards;

}
