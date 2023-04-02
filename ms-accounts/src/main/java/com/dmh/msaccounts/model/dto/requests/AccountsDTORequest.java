package com.dmh.msaccounts.model.dto.requests;

import com.dmh.msaccounts.model.dto.CardsDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountsDTORequest implements Serializable {

    @NotNull(message = "\"userId\" is required.")
    private String userId;
    private Set<CardsDTO> cards;

}
