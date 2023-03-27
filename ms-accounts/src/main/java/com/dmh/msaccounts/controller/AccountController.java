package com.dmh.msaccounts.controller;

import com.dmh.msaccounts.exception.InvalidFieldException;
import com.dmh.msaccounts.model.dto.AccountsDTORequest;
import com.dmh.msaccounts.model.dto.CardsDTORequest;
import com.dmh.msaccounts.response.ResponseHandler;
import com.dmh.msaccounts.service.AccountServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@SecurityRequirement(name = "Bearer Authentication")
@Slf4j
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
                " created.");
    }

    @Operation(summary = "Get account", description = "Get account")
    @GetMapping("/{id}")
    public ResponseEntity<Object> findAccountById(@PathVariable("id") Long id) {
        log.info("qualquer string aí");
        return responseHandler.build(accountService.findAccountById(id), HttpStatus.OK, "Account found.");
    }

//    @PatchMapping("/{id}")
//    @Operation(summary = "Update account", description = "Update account")
//    public ResponseEntity<Object> updateAccount(@RequestBody AccountsPatchDTORequest accountsPatchDTORequest,
//                                                @PathVariable("id") Long id) {
//        accountService.updateAccount(accountsPatchDTORequest, id);
//        return responseHandler.build(null, HttpStatus.OK, "Account updated.");
//    }

    @Operation(summary = "Get all cards", description = "Get all cards")
    @GetMapping("/{accountId}/cards")
    public ResponseEntity<Object> findCardsByAccount(@PathVariable("accountId") Long accountId){
        return responseHandler.build(accountService.findAccountById(accountId), HttpStatus.OK, "Cards found");
    }

    @PostMapping("/{accountId}/cards")
    public ResponseEntity<Object> createCardByAccount(@PathVariable("accountId") Long accountId, @RequestBody CardsDTORequest cardsDTORequest){
        return responseHandler.build(accountService.createCardByAccount(accountId, cardsDTORequest), HttpStatus.CREATED, "Card associated");
    }

    @Operation(summary = "Get card", description = "Get card")
    @GetMapping("/{accountId}/cards/{cardId}")
    public ResponseEntity<Object> findCardOfAccountById(@PathVariable("accountId") Long accountId, @PathVariable("cardId") Long cardId){
        return responseHandler.build(accountService.findAccountCardsById(accountId, cardId), HttpStatus.OK, "Card found");
    }

    @Operation(summary = "Delete card", description = "Delete card")
    @DeleteMapping("/{accountId}/cards/{cardId}")
    public ResponseEntity<Object> deleteCardOfAccountById(@PathVariable("accountId") Long accountId, @PathVariable("cardId") Long cardId) {
        return responseHandler.build(accountService.deleteCardOfAccountById(accountId, cardId), HttpStatus.OK, "Card deleted");
    }

}


