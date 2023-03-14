package com.dmh.msaccounts.service;

import com.dmh.msaccounts.exception.DataNotFoundException;
import com.dmh.msaccounts.model.Accounts;
import com.dmh.msaccounts.model.Cards;
import com.dmh.msaccounts.model.Transactions;
import com.dmh.msaccounts.model.dto.TransactionDTO;
import com.dmh.msaccounts.model.dto.TransactionDtoRequest;
import com.dmh.msaccounts.repository.IAccountsRepository;
import com.dmh.msaccounts.repository.ICardsRepository;
import com.dmh.msaccounts.repository.ITransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    IAccountsRepository accountsRepository;

    @Autowired
    ICardsRepository cardsRepository;

    @Autowired
    ITransactionRepository transactionRepository;

    @Autowired
    private ModelMapper mapper;

    public TransactionDTO transferirValor(TransactionDtoRequest transactionDTO) throws DataNotFoundException {

        Accounts accounts = accountsRepository.findById(transactionDTO.getAccountId()).orElseThrow(() -> new DataNotFoundException("Account not found my son!"));

        Cards cards = cardsRepository.findById(transactionDTO.getCardId()).orElseThrow(() -> {
            throw new DataNotFoundException("Card not found.");
        });


        BigDecimal initialAmmount = accounts.getAmmount();
        BigDecimal transValue = transactionDTO.getValue();

        if(transValue.doubleValue() < 0.0){
            throw new IllegalArgumentException("Not a valid transaction value");
        };
        BigDecimal newAmmount = initialAmmount.add(transValue);
        accounts.setAmmount(newAmmount);

        Transactions transaction = Transactions.builder()
                .cardType(transactionDTO.getCardType())
                .value(transactionDTO.getValue())
                .dateTransaction(new Date())
                .transactionType("cash deposit")
                .description(transactionDTO.getDescription())
                .account(accounts)
                .cards(cards).build();

        accounts.getTransactions().add(transaction);
//      gravar novo saldo no account
        accountsRepository.save(accounts);
        transactionRepository.save(transaction);

        return mapper.map(transactionRepository.save(transaction), TransactionDTO.class);
    };
}
