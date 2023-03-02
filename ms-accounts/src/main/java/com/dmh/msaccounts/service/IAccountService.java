package com.dmh.msaccounts.service;

import com.dmh.msaccounts.model.dto.AccountsDTORequest;
import com.dmh.msaccounts.model.dto.AccountsDTOResponse;
import com.dmh.msaccounts.model.dto.AccountsPatchDTORequest;

public interface IAccountService {

    AccountsDTOResponse createAccount(AccountsDTORequest accountsDTORequest);
    AccountsDTOResponse findAccountById(String id);
    void updateAccount(AccountsPatchDTORequest accountsPatchDTORequest, String id);

}
