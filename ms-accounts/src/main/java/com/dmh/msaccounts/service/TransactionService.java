package com.dmh.msaccounts.service;

import com.dmh.msaccounts.exception.DataNotFoundException;
import com.dmh.msaccounts.model.Accounts;
import com.dmh.msaccounts.model.Transactions;
import com.dmh.msaccounts.model.dto.AccountsDTOResponse;
import com.dmh.msaccounts.model.dto.TransactionDTO;
import com.dmh.msaccounts.repository.IAccountsRepository;
import com.dmh.msaccounts.repository.ITransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    IAccountsRepository accountsRepository;

    @Autowired
    ITransactionRepository transactionRepository;

    @Autowired
    private ModelMapper mapper;

    public TransactionDTO transferirValor(Transactions transaction) throws DataNotFoundException {

        Accounts accountData = accountsRepository.findById(transaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Account not found my son!"));

        BigDecimal initialAmmount = accountData.getAmmount();
        BigDecimal transValue = transaction.getValue();

        if(transValue.doubleValue() < 0.0){
            throw new IllegalArgumentException("Not a valid transaction value");
        };
        BigDecimal newAmmount = initialAmmount.add(transValue);
        accountData.setAmmount(newAmmount);

//      gravar novo saldo no account
        accountsRepository.save(accountData);

        return mapper.map(transactionRepository.save(transaction), TransactionDTO.class);
    };
}
