package com.dmh.msaccounts.controller;

import com.dmh.msaccounts.model.Transactions;
import com.dmh.msaccounts.model.dto.TransactionDtoRequest;
import com.dmh.msaccounts.model.enums.CardsTypeEnum;
import com.dmh.msaccounts.response.ResponseHandler;
import com.dmh.msaccounts.service.IAccountService;
import com.dmh.msaccounts.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<Object> transaction(@PathVariable("id") String id,
                                              @Valid @RequestBody TransactionDtoRequest transactionDtoRequest,
                                              BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()){
            throw new Exception(String.valueOf(bindingResult.getAllErrors().get(0)));
        }

        String accountId = accountService.findAccountById(id).getAccount();

        Transactions transaction = new Transactions(
                transactionDtoRequest.getCardType(),
                accountId,
                transactionDtoRequest.getValue(),
                new Date(),
                "cash deposit",
                transactionDtoRequest.getDescription());

        return responseHandler.build(transactionService.transferirValor(transaction), HttpStatus.OK, "Successfully transferred");
    }
}
