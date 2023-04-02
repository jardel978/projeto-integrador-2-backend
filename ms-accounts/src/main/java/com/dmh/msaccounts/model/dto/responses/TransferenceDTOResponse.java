package com.dmh.msaccounts.model.dto.responses;

import com.dmh.msaccounts.model.dto.TransactionDTO;
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
public class TransferenceDTOResponse extends TransactionDTO implements Serializable {

    private String accountOrigin;
    private String accountDestiny;

}
