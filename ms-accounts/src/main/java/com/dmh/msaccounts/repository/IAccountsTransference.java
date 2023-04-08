package com.dmh.msaccounts.repository;

public interface IAccountsTransference {

    Long getId();

    AccountDestinyInfo getAccountDestinyInfo();

    interface AccountDestinyInfo {

        Long getId();

        String getAccount();

        String getUserId();

    }

}
