package com.dmh.msaccounts.repository;

import com.dmh.msaccounts.model.Accounts;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IAccountsRepository extends MongoRepository<Accounts, String> {
}
