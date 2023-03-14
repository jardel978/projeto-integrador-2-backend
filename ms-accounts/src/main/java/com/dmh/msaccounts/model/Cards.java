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
    @Column(name = "cards_id")
    private String cardId;
    private String number;
    private String expirationDate;
    private String cvc;
    private String name;
    private BigDecimal ammount;
    @Column(name = "is_external")
    private boolean isExternal;
    @Column(name = "card_type")
    private CardsTypeEnum cardType;

    @ManyToOne
    private Accounts account;

    @OneToMany
    @JoinColumn(name = "cards_id")
    private Set<Transactions> transactions = new HashSet<>() ;
}
