package com.dmh.msaccounts.controller;

import com.dmh.msaccounts.exception.DataNotFoundException;
import com.dmh.msaccounts.model.Accounts;
import com.dmh.msaccounts.model.Cards;
import com.dmh.msaccounts.model.Transactions;
import com.dmh.msaccounts.model.dto.TransactionDtoRequest;
import com.dmh.msaccounts.model.enums.CardsTypeEnum;
import com.dmh.msaccounts.repository.IAccountsRepository;
import com.dmh.msaccounts.repository.ICardsRepository;
import com.dmh.msaccounts.response.ResponseHandler;
import com.dmh.msaccounts.service.IAccountService;
import com.dmh.msaccounts.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "Bearer Authentication")
public class TransactionController {



    @Autowired
    TransactionService transactionService;

    @Autowired
    ResponseHandler responseHandler;

    @PostMapping("/{id}/transactions")
    public ResponseEntity<Object> transaction(@PathVariable("id") String accountId,
                                              @Valid @RequestBody TransactionDtoRequest transactionDtoRequest,
                                              BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()){
            throw new Exception(String.valueOf(bindingResult.getAllErrors().get(0)));
        }
        return responseHandler.build(transactionService.transferirValor(transactionDtoRequest), HttpStatus.OK, "Successfully transferred");
    }
}
