package com.dmh.msaccounts.controller;

import com.dmh.msaccounts.model.dto.TransactionDTO;
import com.dmh.msaccounts.model.dto.requests.DepositDTORequest;
import com.dmh.msaccounts.model.dto.requests.TransferenceDTORequest;
import com.dmh.msaccounts.response.ResponseHandler;
import com.dmh.msaccounts.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@SecurityRequirement(name = "Bearer Authentication")
public class TransactionController {


    @Autowired
    TransactionService transactionService;

    @Autowired
    ResponseHandler responseHandler;

    @PostMapping("/{id}/transactions")
    public ResponseEntity<Object> depositingValue(@PathVariable("id") Long accountId,
                                                  @Valid @RequestBody DepositDTORequest depositDTORequest,
                                                  BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new Exception(String.valueOf(bindingResult.getAllErrors().get(0)));
        }
        return responseHandler.build(transactionService.depositingValue(accountId, depositDTORequest), HttpStatus.OK,
                "Successfully " +
                        "transferred");
    }

    //    /{id}/activity?page=0&size=10&sort=dateTransaction,asc
    @GetMapping("/{id}/activity")
    public ResponseEntity<Object> findAll(Pageable pageable, @PathVariable("id") Long accountOriginId) {
        String message = "Found transactions.";
        List<TransactionDTO> transactionDTOList = transactionService.findAll(pageable, accountOriginId);
        if (transactionDTOList.isEmpty()) {
            message = "No transaction found.";
        }
        return responseHandler.build(transactionDTOList, HttpStatus.OK, message);
    }

    @GetMapping("/{id}/transactions-range")
    public ResponseEntity<Object> findAllTransferencesByRangeValue(@PathVariable("id") Long accountOriginId,
                                                                   @RequestParam("start") BigDecimal startValue,
                                                                   @RequestParam("end") BigDecimal endValue) {
        String message = "Found transactions.";
        List<TransactionDTO> transactions = transactionService.findAllTransferenceByValueRange(accountOriginId,
                startValue, endValue);
        if (transactions.isEmpty()) {
            message = "No transactions found";
        }
        return responseHandler.build(transactions, HttpStatus.OK, message);
    }

    @GetMapping("/{id}/activity/{transactId}")
    public ResponseEntity<Object> findByAccountOriginIdAndTransactionId(@PathVariable("id") Long accountOriginId,
                                                                        @PathVariable("transactId") Long transactionId) {
        return responseHandler.build(transactionService.findByAccountOriginIdAndTransactionId(accountOriginId,
                transactionId), HttpStatus.OK, "Transaction found.");
    }

    @PostMapping("/{id}/transferences")
    public ResponseEntity<Object> transferences(@PathVariable("id") Long accountId,
                                                @Valid @RequestBody TransferenceDTORequest transferenceDTORequest,
                                                BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new Exception(String.valueOf(bindingResult.getAllErrors().get(0)));
        }
        return responseHandler.build(transactionService.transferringValue(accountId, transferenceDTORequest), HttpStatus.OK,
                "Successfully transferred transaction");

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
        return responseHandler.build(transactionService.getLast5AccountsDestiny(accountId), HttpStatus.OK, "Last 5 " +
                "accounts destiny.");
    }

    @GetMapping("/{id}/transferences/document")
    public void getVoucher(@PathVariable("id") Long tranferenceId, HttpServletRequest request,
                           HttpServletResponse response) {
        transactionService.getVoucher(tranferenceId, request, response);
    }

}
