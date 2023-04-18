package com.dmh.msaccounts.model;

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
//@Table(name = "transferences")
@DiscriminatorValue("transference")
public class Transferences extends Transactions implements Serializable {

    @ManyToOne
    @JoinColumn(name = "account_destiny_id", foreignKey = @ForeignKey(name = "fk_account_transference"))
    private Accounts accountsDestiny;

}
