package com.dmh.msaccounts.repository;

import com.dmh.msaccounts.model.Cards;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICardsRepository extends JpaRepository<Cards, Long> {
}
