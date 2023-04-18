package com.dmh.msaccounts.model.dto.requests;

import com.dmh.msaccounts.model.dto.CardsDTO;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountsPatchDTORequest implements Serializable {

    //    private String userId;
    private Long id;
    private Set<CardsDTO> cards;

}
