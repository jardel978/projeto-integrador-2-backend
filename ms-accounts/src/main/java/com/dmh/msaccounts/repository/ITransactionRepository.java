package com.dmh.msaccounts.repository;

import com.dmh.msaccounts.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITransactionRepository extends JpaRepository<Transactions, Long> {
    List<Transactions> findTop5ByAccountIdOrderByDateTransactionDesc(String accountId);
}
