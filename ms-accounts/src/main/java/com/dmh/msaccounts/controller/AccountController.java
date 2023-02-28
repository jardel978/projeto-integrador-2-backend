package com.dmh.msaccounts.controller;

import com.dmh.msaccounts.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;
}
