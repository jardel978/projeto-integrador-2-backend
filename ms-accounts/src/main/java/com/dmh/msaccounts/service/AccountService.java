package com.dmh.msaccounts.service;


import com.dmh.msaccounts.model.dto.AccountsDTORequest;
import com.dmh.msaccounts.model.dto.AccountsDTOResponse;
import com.dmh.msaccounts.repository.FeignUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class AccountService {

    @Autowired
    private FeignUserRepository feignUserRepository;

    public AccountsDTOResponse createAccount(AccountsDTORequest accountsDTORequest) {


    }


}
