package com.dmh.msaccounts.service;

import com.dmh.msaccounts.model.dto.*;

import java.util.Optional;

public interface IAccountService {

    AccountsDTOResponse createAccount(AccountsDTORequest accountsDTORequest);
    AccountsDTOResponse findAccountById(Long id);
//    void updateAccount(AccountsPatchDTORequest accountsPatchDTORequest, Long id);
    CardsDTO findAccountCardsById(Long accountId, Long cardId);
    AccountsDTORequest deleteCardOfAccountById(Long accountId, Long cardId);
    CardsDTO createCardByAccount(Long accountId, CardsDTORequest card);

}
