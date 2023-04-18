package com.dmh.msaccounts.model;

import com.dmh.msaccounts.model.enums.CardsTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cards")
public class Cards implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String expirationDate;
    private String cvc;
    private String name;
    private BigDecimal ammount;
    @Column(name = "card_type")
    private CardsTypeEnum cardType;

    @ManyToOne
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "fk_account_card"))
    private Accounts account;

    @OneToMany(mappedBy = "card")
    @ToString.Exclude
//    @JoinColumn(name = "card_id", foreignKey = @ForeignKey(name = "fk_transaction_card"))
    private Set<Deposit> transactions = new HashSet<>() ;

}
