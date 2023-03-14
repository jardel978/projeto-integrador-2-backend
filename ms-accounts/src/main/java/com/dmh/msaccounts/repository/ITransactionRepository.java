package com.dmh.msaccounts.repository;

import com.dmh.msaccounts.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransactionRepository extends JpaRepository<Transactions, String> {
}
