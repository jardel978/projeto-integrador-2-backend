package com.dmh.msaccounts.controller;

import com.dmh.msaccounts.model.Transactions;
import com.dmh.msaccounts.model.enums.CardsTypeEnum;
import com.dmh.msaccounts.response.ResponseHandler;
import com.dmh.msaccounts.service.IAccountService;
import com.dmh.msaccounts.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/accounts")
public class TransactionController {


    @Autowired
    IAccountService accountService;
    @Autowired
    TransactionService transactionService;

    @Autowired
    ResponseHandler responseHandler;

    @PostMapping("/{id}/transactions")
    public ResponseEntity<Object> transaction(@NotNull @RequestBody CardsTypeEnum cardType,
                                              @PathVariable("id") String id,
                                              @NotNull @RequestBody Double value,
                                              @RequestBody String description){

        String accountId = accountService.findAccountById(id).getAccount();

        Transactions transaction = new Transactions(cardType, accountId, new BigDecimal(value), new Date(), "cash deposit", description);
        return responseHandler.build(transactionService.transferirValor(transaction), HttpStatus.OK, "Successfully transferred");
    }
}
