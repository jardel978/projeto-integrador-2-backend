package com.dmh.msaccounts.service;

import com.dmh.msaccounts.model.dto.*;

import java.util.Optional;

public interface IAccountService {

    AccountsDTOResponse createAccount(AccountsDTORequest accountsDTORequest);
    AccountsDTOResponse findAccountById(String id);
    void updateAccount(AccountsPatchDTORequest accountsPatchDTORequest, String id);
    AccountsDTORequest findAccountCardsById(String accountId, String cardId);
    AccountsDTORequest deleteCardOfAccountById(String accountId, String cardId);
    CardsDTO createCardByAccount(String accountId, CardsDTORequest card);

}
