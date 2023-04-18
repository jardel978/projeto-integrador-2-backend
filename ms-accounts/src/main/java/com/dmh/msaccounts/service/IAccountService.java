package com.dmh.msaccounts.service;

import com.dmh.msaccounts.model.dto.*;
import com.dmh.msaccounts.model.dto.requests.AccountsDTORequest;
import com.dmh.msaccounts.model.dto.requests.CardsDTORequest;
import com.dmh.msaccounts.model.dto.responses.AccountsDTOResponse;

public interface IAccountService {

    AccountsDTOResponse createAccount(AccountsDTORequest accountsDTORequest, boolean createUserWithAccount);
    AccountsDTOResponse findAccountById(Long id);
//    void updateAccount(AccountsPatchDTORequest accountsPatchDTORequest, Long id);
    CardsDTO findAccountCardsById(Long accountId, Long cardId);
    AccountsDTORequest deleteCardOfAccountById(Long accountId, Long cardId);
    CardsDTO createCardByAccount(Long accountId, CardsDTORequest card);

}
