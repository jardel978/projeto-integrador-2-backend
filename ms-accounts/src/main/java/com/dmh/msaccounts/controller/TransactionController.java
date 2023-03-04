package com.dmh.msaccounts.controller;

import com.dmh.msaccounts.model.Transactions;
import com.dmh.msaccounts.model.enums.CardsTypeEnum;
import com.dmh.msaccounts.response.ResponseHandler;
import com.dmh.msaccounts.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.PathParam;
import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/accounts")
public class TransactionController {

    @Autowired
    TransactionService service;

    @Autowired
    ResponseHandler responseHandler;

    @PostMapping("/{id}/transactions")
    public ResponseEntity<Object> transaction(@Valid @RequestBody CardsTypeEnum cardType,
                                              @PathVariable("id") String id,
                                              @RequestBody Double value,
                                              @RequestBody String description){

        Transactions transaction = new Transactions(cardType, id, new BigDecimal(value), new Date(), "cash deposit", description);
        return responseHandler.build(service.transferirValor(transaction), HttpStatus.OK, "Successfully transferred");
    }
}
