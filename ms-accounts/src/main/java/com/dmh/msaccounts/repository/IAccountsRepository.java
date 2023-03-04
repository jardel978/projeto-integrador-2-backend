package com.dmh.msaccounts.repository;

import com.dmh.msaccounts.model.Accounts;
import com.dmh.msaccounts.model.dto.AccountsDTORequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IAccountsRepository extends MongoRepository<Accounts, String> {
    Optional<AccountsDTORequest> findAccountCardsById(String accountId, String cardId);
}
