package com.dmh.msaccounts.model;

import com.dmh.msaccounts.model.enums.CardsTypeEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(name = "deposits")
@DiscriminatorValue("deposit")
public class Deposit extends Transactions implements Serializable {

    @Column(name = "card_type")
    private CardsTypeEnum cardType;

    @ManyToOne
    @JoinColumn(name = "card_id", foreignKey = @ForeignKey(name = "fk_card_deposit"))
    private Cards card;

}

