package com.dmh.msaccounts.service;

import com.dmh.msaccounts.exception.DataNotFoundException;
import com.dmh.msaccounts.model.*;
import com.dmh.msaccounts.model.dto.DepositDTO;
import com.dmh.msaccounts.model.dto.TransactionDTO;
import com.dmh.msaccounts.model.dto.TransferenceDTO;
import com.dmh.msaccounts.repository.IAccountsRepository;
import com.dmh.msaccounts.repository.ICardsRepository;
import com.dmh.msaccounts.repository.ITransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public TransactionDTO transferirValor(DepositDTO depositDTO) throws DataNotFoundException {

        Accounts account = accountsRepository.findById(depositDTO.getAccountOriginId()).orElseThrow(() -> new DataNotFoundException(
                "Account not found my son!"));

        Cards card = cardsRepository.findById(depositDTO.getCardId()).orElseThrow(() -> {
            throw new DataNotFoundException("Card not found.");
        });


        BigDecimal initialAmmount = account.getAmmount();
        BigDecimal transValue = depositDTO.getValue();

        if (transValue.doubleValue() < 0.0) {
            throw new IllegalArgumentException("Not a valid transaction value");
        }

        BigDecimal newAmmount = initialAmmount.add(transValue);
        account.setAmmount(newAmmount);

        Deposit deposit = Deposit.builder()
                .cardType(depositDTO.getCardType())
                .value(depositDTO.getValue())
                .dateTransaction(new Date())
                .transactionType("cash deposit")
                .description(depositDTO.getDescription())
                .accountOrigin(account)
                .card(card).build();

//      accounts.getTransactions().add(transaction);
//      gravar novo saldo no account
        accountsRepository.save(account);

        return mapper.map(transactionRepository.save(deposit), DepositDTO.class);
    }

    public TransactionDTO transferirValor(TransferenceDTO transferenceDTO) throws DataNotFoundException{
        Accounts accountDestination = accountsRepository.findById(transferenceDTO.getAccountId()).orElseThrow(() -> new DataNotFoundException("Account of destiny not found, my consagrated"));

        Accounts accountOrigin = accountsRepository.findById(transferenceDTO.getAccountOriginId()).orElseThrow(() -> new DataNotFoundException("Origin account not found."));




        BigDecimal intialAmmount = accountOrigin.getAmmount();
        BigDecimal transferenceValue = transferenceDTO.getValue();

        if (transferenceValue.doubleValue() < 0.0) {
            throw new IllegalArgumentException("Not a valid Transference value!");
        }


            BigDecimal newAmmount = intialAmmount.subtract(transferenceValue);
            accountOrigin.setAmmount(newAmmount);

            Transferences transference = Transferences.builder()
                    .accountsDestiny(accountDestination)
                    .value(transferenceDTO.getValue())
                    .dateTransaction(transferenceDTO.getDateTransaction())
                    .transactionType(transferenceDTO.getTransactionType())
                    .description(transferenceDTO.getDescription())
                    .accountOrigin(accountOrigin)
                    .build();
            accountsRepository.save(accountOrigin);

            return mapper.map(transactionRepository.save(transference), TransferenceDTO.class);


    }

    public List<TransactionDTO> getLast5Transactions(String accountId) {
        List<Transactions> depositList =
                transactionRepository.findTop5ByAccountOriginIdOrderByDateTransactionDesc(accountId);

        return depositList.stream().map(transaction -> {
            return mapper.map(transaction, TransactionDTO.class);
        }).collect(Collectors.toList());
    }
}
