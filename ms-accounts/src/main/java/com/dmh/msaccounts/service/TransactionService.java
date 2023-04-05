package com.dmh.msaccounts.service;

import com.dmh.msaccounts.exception.DataNotFoundException;
import com.dmh.msaccounts.exception.InsufficientFundsException;
import com.dmh.msaccounts.model.*;
import com.dmh.msaccounts.model.dto.DepositDTO;
import com.dmh.msaccounts.model.dto.TransactionDTO;
import com.dmh.msaccounts.model.dto.TransferenceDTO;
import com.dmh.msaccounts.model.dto.requests.DepositDTORequest;
import com.dmh.msaccounts.model.dto.requests.TransferenceDTORequest;
import com.dmh.msaccounts.model.dto.responses.AccountTransferenceDTOResponse;
import com.dmh.msaccounts.model.dto.responses.TransferenceDTOResponse;
import com.dmh.msaccounts.repository.FeignUserRepository;
import com.dmh.msaccounts.repository.IAccountsRepository;
import com.dmh.msaccounts.repository.ICardsRepository;
import com.dmh.msaccounts.repository.ITransactionRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private IAccountsRepository accountsRepository;

    @Autowired
    private ICardsRepository cardsRepository;

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    private FeignUserRepository feignUserRepository;

    @Autowired
    private ModelMapper mapper;

    public TransactionDTO depositingValue(Long accountId, DepositDTORequest depositDTORequest) throws DataNotFoundException {

        Accounts account = accountsRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException(
                "Account not found my son!"));

        Cards card = cardsRepository.findById(depositDTORequest.getCardId()).orElseThrow(() -> {
            throw new DataNotFoundException("Card not found.");
        });


        BigDecimal initialAmmount = account.getAmmount();
        BigDecimal transValue = depositDTORequest.getValue();

        if (transValue.doubleValue() < 0.0) {
            throw new IllegalArgumentException("Not a valid transaction value");
        }

        BigDecimal newAmmount = initialAmmount.add(transValue);
        account.setAmmount(newAmmount);

        Deposit deposit = Deposit.builder()
                .cardType(card.getCardType())
                .value(transValue)
                .dateTransaction(new Date())
                .transactionType("cash deposit")
                .description(depositDTORequest.getDescription())
                .accountOrigin(account)
                .card(card).build();

//      accounts.getTransactions().add(transaction);
//      gravar novo saldo no account
        accountsRepository.save(account);

        return mapper.map(transactionRepository.saveAndFlush(deposit), DepositDTO.class);
    }

    public List<TransactionDTO> findAll(Pageable pageable, Long accountOriginId) {
        accountsRepository.findById(accountOriginId).orElseThrow(() -> {
            throw new DataNotFoundException("Account not found.");
        });
        Page<Transactions> transactionsPage = transactionRepository.findAllByAccountOriginId(pageable, accountOriginId);
        return transactionsPage.stream().map(transactions -> {
            TransactionDTO transactionDTO = null;
            if (transactions instanceof Deposit)
                transactionDTO = mapper.map(transactions, DepositDTO.class);
            if (transactions instanceof Transferences)
                transactionDTO = mapper.map(transactions, TransferenceDTOResponse.class);

            return transactionDTO;
        }).collect(Collectors.toList());
    }

    public TransactionDTO findByAccountOriginIdAndTransactionId(Long accountOriginId, Long transactionId) {
        Transactions transactionsModel = transactionRepository.findByAccountOriginIdAndId(accountOriginId,
                transactionId).orElseThrow(() -> {
            throw new DataNotFoundException("Transaction not found.");
        });
        if (transactionsModel instanceof Deposit)
            return mapper.map(transactionsModel, DepositDTO.class);
        if (transactionsModel instanceof Transferences)
            return mapper.map(transactionsModel, TransferenceDTOResponse.class);

        return null;
    }

    public TransactionDTO transferringValue(Long accountOriginId, TransferenceDTORequest transferenceDTORequest) throws DataNotFoundException {
        if (accountOriginId.equals(transferenceDTORequest.getAccountDestinyId()))
            throw new IllegalArgumentException("The source account must be different from the destination account.");

        Accounts accountOrigin =
                accountsRepository.findById(accountOriginId).orElseThrow(() -> new DataNotFoundException(
                        "Origin account not found."));

        Accounts accountDestination =
                accountsRepository.findById(transferenceDTORequest.getAccountDestinyId()).orElseThrow(() -> new DataNotFoundException(
                        "Account of destiny not found, my consagrated"));

        BigDecimal intialAmmountOrigin = accountOrigin.getAmmount();
        BigDecimal intialAmmountDestiny = accountDestination.getAmmount();
        BigDecimal transferenceValue = transferenceDTORequest.getValue();

        if (transferenceValue.doubleValue() < 0.0) {
            throw new IllegalArgumentException("Not a valid Transference value!");
        }

        if (accountOrigin.getAmmount().compareTo(transferenceValue) < 0)
            throw new InsufficientFundsException("Insufficient balance for transfer.");

        BigDecimal newAmmountOrigin = intialAmmountOrigin.subtract(transferenceValue);
        accountOrigin.setAmmount(newAmmountOrigin);
        BigDecimal newAmmountDestiny = intialAmmountDestiny.add(transferenceValue);
        accountDestination.setAmmount(newAmmountDestiny);

        Transferences transference = Transferences.builder()
                .accountsDestiny(accountDestination)
                .value(transferenceValue)
                .dateTransaction(new Date())
                .transactionType("bank transfer")
                .description(transferenceDTORequest.getDescription())
                .accountOrigin(accountOrigin)
                .accountsDestiny(accountDestination)
                .build();

        List<Accounts> accountsList = new ArrayList<>();
        accountsList.add(accountOrigin);
        accountsList.add(accountDestination);
        accountsRepository.saveAll(accountsList);

        return mapper.map(transactionRepository.saveAndFlush(transference), TransferenceDTO.class);
    }

    public List<TransactionDTO> getLast5Transactions(Long accountId) {
        List<Transactions> transactions =
                transactionRepository.findTop5ByAccountOriginIdOrderByDateTransactionDesc(accountId);

        return transactions.stream().map(transaction -> {
            if (transaction.getClass().equals(Deposit.class))
                return mapper.map(transaction, DepositDTO.class);
            else
                return mapper.map(transaction, TransferenceDTOResponse.class);

        }).collect(Collectors.toList());
    }

    public List<AccountTransferenceDTOResponse> getLast5AccountsDetiny(Long accountId) {
        List<Transferences> depositList =
                transactionRepository.findTop5DistinctAccountsDestinyByAccountOriginIdIsNot(accountId);

        return depositList.stream().map(transaction -> {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            User user = objectMapper.convertValue(feignUserRepository.findByUserId(transaction.getAccountsDestiny().getUserId()).getBody().get(
                    "data"), User.class);
            AccountTransferenceDTOResponse transferenceDTOResponse = AccountTransferenceDTOResponse.builder()
                    .accountDestiny(transaction.getAccountsDestiny().getAccount())
                    .recipient(user.getName() + " " + user.getLastName())
                    .dateTransaction(transaction.getDateTransaction()).build();
            return transferenceDTOResponse;
        }).collect(Collectors.toList());
    }

    public List<TransactionDTO> getLast10Transferences(Long accountId) {
        List<Transactions> transferences =
                transactionRepository.findByAccountOriginIdOrderByDateTransactionDescLimitedTo(accountId, 10);

        return transferences.stream().map(transference -> mapper.map(transference, TransferenceDTOResponse.class)).collect(Collectors.toList());
    }

}
