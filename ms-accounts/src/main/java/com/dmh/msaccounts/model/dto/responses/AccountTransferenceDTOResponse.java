package com.dmh.msaccounts.model.dto.responses;

import com.dmh.msaccounts.model.dto.CardsDTO;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransferenceDTOResponse implements Serializable {

    private String accountDestiny;
    private String recipient;
    private Date dateTransaction;

}
