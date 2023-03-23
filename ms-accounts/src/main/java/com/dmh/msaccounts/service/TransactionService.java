package com.dmh.msaccounts.service;

import com.dmh.msaccounts.exception.DataNotFoundException;
import com.dmh.msaccounts.model.*;
import com.dmh.msaccounts.model.dto.DepositDTO;
import com.dmh.msaccounts.model.dto.TransactionDTO;
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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
                transactionRepository.findTop5DistinctAccountsDestinyByAccountOriginId(accountId);

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
