package com.dmh.msaccounts.model;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
@Table(name = "accounts")
public class Accounts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String account;
    @Column(name = "user_id")
    private String userId;
    private BigDecimal ammount;

    @OneToMany
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "fk_card_account"))
    @ToString.Exclude
    private Set<Cards> cards = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "fk_transaction_account"))
    @ToString.Exclude
    private Set<Transactions> transactions = new HashSet<>();

}
