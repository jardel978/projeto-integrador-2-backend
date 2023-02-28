package com.dmh.msaccounts.model;

import com.dmh.msaccounts.model.enums.CardsTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cards")
public class Cards implements Serializable {

    @Id
    private String id;
    private String number;
    private String expirationDate;
    private String cvc;
    private String name;
    private String accountId;
    @Field(name = "card_type")
    private CardsTypeEnum cardType;
}
