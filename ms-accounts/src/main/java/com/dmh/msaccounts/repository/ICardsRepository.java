package com.dmh.msaccounts.repository;

import com.dmh.msaccounts.model.Cards;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ICardsRepository extends MongoRepository<Cards, String> {
}
