package com.dmh.msaccounts.controller;

import com.dmh.msaccounts.exception.InvalidFieldException;
import com.dmh.msaccounts.model.dto.AccountsDTORequest;
import com.dmh.msaccounts.model.dto.CardsDTO;
import com.dmh.msaccounts.model.dto.CardsDTORequest;
import com.dmh.msaccounts.response.ResponseHandler;
import com.dmh.msaccounts.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    ResponseHandler responseHandler;

    @PostMapping
    public ResponseEntity<Object> createAccount(@Valid @RequestBody AccountsDTORequest accountsDTORequest,
                                                BindingResult bdResult) {
        if (bdResult.hasErrors()) {
            throw new InvalidFieldException(bdResult.getAllErrors().get(0).getDefaultMessage());
        }
        return responseHandler.build(accountService.createAccount(accountsDTORequest), HttpStatus.CREATED, "Account " +
                "created.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findAccountById(@PathVariable("id") String id) {
        return responseHandler.build(accountService.findAccountById(id), HttpStatus.OK, "Account found.");
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<Object> updateAccount(@RequestBody AccountsPatchDTORequest accountsPatchDTORequest,
//                                                @PathVariable("id") String id) {
//        accountService.updateAccount(accountsPatchDTORequest, id);
//        return responseHandler.build(null, HttpStatus.OK, "Account updated.");
//    }


//    TASK 12, 13 e 14
    @GetMapping("/{accountId}/cards")
    public ResponseEntity<Object> findCardsByAccount(@PathVariable("accountId") String accountId){
        return responseHandler.build(accountService.findAccountById(accountId), HttpStatus.OK, "Cards found");
    }

    @PostMapping("/{accountId}/cards")
    public ResponseEntity<Object> createCardByAccount(@PathVariable("accountId") String accountId, @RequestBody CardsDTORequest cardsDTORequest){
        return responseHandler.build(accountService.createCardByAccount(accountId, cardsDTORequest), HttpStatus.CREATED, "Card associated");
    }

    @GetMapping("/{accountId}/cards/{cardId}")
    public ResponseEntity<Object> findCardOfAccountById(@PathVariable("accountId") String accountId, @PathVariable("cardId") String cardId){
        return responseHandler.build(accountService.findAccountCardsById(accountId, cardId), HttpStatus.OK, "Card found");
    }

    @DeleteMapping("/{accountId}/cards/{cardId}")
    public ResponseEntity<Object> deleteCardOfAccountById(@PathVariable("accountId") String accountId, @PathVariable("cardId") String cardId) {
        return responseHandler.build(accountService.deleteCardOfAccountById(accountId, cardId), HttpStatus.OK, "Card deleted");
    }

}