package com.dmh.msaccounts.repository;

import com.dmh.msaccounts.model.Transactions;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ITransactionRepository extends MongoRepository<Transactions, String> {
}
