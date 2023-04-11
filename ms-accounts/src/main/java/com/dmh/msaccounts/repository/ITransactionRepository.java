package com.dmh.msaccounts.repository;

import com.dmh.msaccounts.model.Transactions;
import com.dmh.msaccounts.model.Transferences;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ITransactionRepository extends JpaRepository<Transactions, Long> {

    Page<Transactions> findAllByAccountOriginId(Pageable pageable, Long accountOriginId);

    Optional<Transactions> findByAccountOriginIdAndId(Long accountOriginId, Long id);

    //    @Query("from Deposit")
    List<Transactions> findTop5ByAccountOriginIdOrderByDateTransactionDesc(Long accountId);

    @Query("from Transferences")
    List<Transactions> findTop5ByAccountOriginIdDistinctAccountsDestiny(Long accountOriginId);

    @Query("from Transferences")
    Transferences findFirstByAccountDestinyId(Long accountDestinyId);

    @Query("from Transferences")
    List<Transactions> findByAccountOriginIdOrderByDateTransactionDescLimitedTo(Long accountId, int limit);

    List<Transactions> findAllByAccountOriginIdAndValueBetween(Long accountOriginId, BigDecimal startValue,
                                                               BigDecimal endValue);

}
