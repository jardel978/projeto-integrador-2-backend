package com.dmh.msaccounts.service;


import com.dmh.msaccounts.model.dto.AccountsDTORequest;
import com.dmh.msaccounts.model.dto.AccountsDTOResponse;
import com.dmh.msaccounts.model.dto.AccountsPatchDTORequest;
import com.dmh.msaccounts.repository.FeignUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private FeignUserRepository feignUserRepository;

    @Override
    public AccountsDTOResponse createAccount(AccountsDTORequest accountsDTORequest) {

    }

    @Override
    public AccountsDTOResponse findAccountById(String id) {

    }

    @Override
    public boolean updateAccount(AccountsPatchDTORequest accountsPatchDTORequest, String id) {
        return true;
    }

}
