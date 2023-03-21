package com.dmh.msaccounts.repository;

import com.dmh.msaccounts.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITransactionRepository extends JpaRepository<Transactions, Long> {

//    @Query("from Deposit")
    List<Transactions> findTop5ByAccountOriginIdOrderByDateTransactionDesc(Long accountId);

    @Query("from Transferences")
    List<Transactions> findTop5DistinctAccountsDestinyByAccountOriginId(Long accountId);

    @Query("from Transferences")
    List<Transactions> findTop10ByAccountOriginIdOrderByDateTransactionDesc(Long accountId);

}
