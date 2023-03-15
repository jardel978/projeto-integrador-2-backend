package com.dmh.msaccounts.repository;

import com.dmh.msaccounts.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountsRepository extends JpaRepository<Accounts, Long> {

}
