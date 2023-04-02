package com.dmh.msaccounts.repository;

import com.dmh.msaccounts.model.Transactions;
import com.dmh.msaccounts.model.Transferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITransactionRepository extends JpaRepository<Transactions, Long> {

//    @Query("from Deposit")
    List<Transactions> findTop5ByAccountOriginIdOrderByDateTransactionDesc(Long accountId);

    // TODO rever query: retorna não só as trasnferëncias destino
    @Query("from Transferences")
    List<Transferences> findTop5DistinctAccountsDestinyByAccountOriginIdIsNot(Long accountOriginId);

    @Query("from Transferences")
    List<Transactions> findByAccountOriginIdOrderByDateTransactionDescLimitedTo(Long accountId, int limit);

}
