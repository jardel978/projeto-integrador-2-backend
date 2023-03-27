package com.dmh.msaccounts.model.dto.requests;

import com.dmh.msaccounts.model.dto.CardsDTO;
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

    @NotNull(message = "\"userId\" is required.")
    private String userId;
    private Set<CardsDTO> cards;

}
