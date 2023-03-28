package com.dmh.msaccounts.controller;

import com.dmh.msaccounts.model.dto.DepositDTO;
import com.dmh.msaccounts.model.dto.TransferenceDTO;
import com.dmh.msaccounts.response.ResponseHandler;
import com.dmh.msaccounts.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accountsss")
@SecurityRequirement(name = "Bearer Authentication")
public class TransactionController {


    @Autowired
    TransactionService transactionService;

    @Autowired
    ResponseHandler responseHandler;

    @PostMapping("/{id}/transactions")
    public ResponseEntity<Object> transaction(@PathVariable("id") String accountId,
                                              @Valid @RequestBody DepositDTO depositDTO,
                                              BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new Exception(String.valueOf(bindingResult.getAllErrors().get(0)));
        }
        return responseHandler.build(transactionService.transferirValor(depositDTO), HttpStatus.OK, "Successfully " +
                "transferred");
    }

    @PostMapping("/{Id}/transferences")
    public ResponseEntity<Object> transferences(@PathVariable("id") String accountId,
                                                @Valid @RequestBody TransferenceDTO transferenceDTO,
                                                BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new Exception(String.valueOf(bindingResult.getAllErrors().get(0)));
        }
        return responseHandler.build(transactionService.transferirValor(transferenceDTO), HttpStatus.OK, "Successfully transferred transaction");

    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<Object> getLastTransactions(@PathVariable("id") Long accountId) {
        return responseHandler.build(transactionService.getLast5Transactions(accountId), HttpStatus.OK, "Transactions" +
                " found.");
    }

    @GetMapping("/{id}/transferences")
    public ResponseEntity<Object> getLast10Trasferences(@PathVariable("id") Long accountId) {
        return responseHandler.build(transactionService.getLast10Transferences(accountId), HttpStatus.OK,
                "Transferences found.");
    }

    @GetMapping("/{id}/transferences/accounts")
    public ResponseEntity<Object> getLastAccountsDestiny(@PathVariable("id") Long accountId) {
        return responseHandler.build(transactionService.getLast5AccountsDetiny(accountId), HttpStatus.OK, "Last 5 " +
                "accounts destiny.");
    }

}
