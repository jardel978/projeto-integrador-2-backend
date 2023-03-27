package com.dmh.msaccounts.model;

import com.dmh.msaccounts.model.enums.CardsTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
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
//    @JoinColumn(name = "card_id", foreignKey = @ForeignKey(name = "fk_transaction_card"))
    private Set<Deposit> transactions = new HashSet<>() ;

}
