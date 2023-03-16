package com.dmh.msaccounts.model;

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
    @JoinColumn(name = "account_id")
    private Set<Cards> cards = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "account_id")
    private Set<Transactions> transactions = new HashSet<>();

}
