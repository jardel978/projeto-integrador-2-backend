package com.dmh.msaccounts.controller;

import com.dmh.msaccounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountController {

    @Autowired
    private AccountService accountService;
}
