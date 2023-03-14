package com.dmh.msaccounts.service;


import com.dmh.msaccounts.exception.DataAlreadyExistsException;
import com.dmh.msaccounts.exception.DataNotFoundException;
import com.dmh.msaccounts.model.Accounts;
import com.dmh.msaccounts.model.Cards;
import com.dmh.msaccounts.model.dto.*;
import com.dmh.msaccounts.repository.FeignUserRepository;
import com.dmh.msaccounts.repository.IAccountsRepository;
import com.dmh.msaccounts.repository.ICardsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private FeignUserRepository feignUserRepository;

    @Autowired
    private IAccountsRepository accountsRepository;

    @Autowired
    private ICardsRepository cardsRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public AccountsDTOResponse createAccount(AccountsDTORequest accountsDTORequest) {
        Accounts accountsModel = mapper.map(accountsDTORequest, Accounts.class);
        ResponseEntity<Map<String, Object>> response = (ResponseEntity<Map<String, Object>>) feignUserRepository.findByUserId(accountsDTORequest.getUserId());
        log.info("response: " + response.getBody().toString());
        if (response.getBody().containsKey("error")) {
            throw new DataNotFoundException("User not found.");
        }
        UUID uuid = UUID.nameUUIDFromBytes(accountsModel.getUserId().getBytes(StandardCharsets.UTF_8));
        String accountNumber = Long.toString(uuid.getMostSignificantBits()).substring(0, 5);
        accountsModel.setAccount(accountNumber);
        accountsModel.setAmmount(new BigDecimal(0));

        if (!accountsModel.getCards().isEmpty()) {

            accountsModel.getCards().stream().map(cards -> {
                return cardsRepository.save(cards);
            }).collect(Collectors.toSet());

        }

        return mapper.map(accountsRepository.save(accountsModel), AccountsDTOResponse.class);
    }

    @Override
    public CardsDTO createCardByAccount(String accountId, CardsDTORequest cardsDTORequest) {
        Accounts accounts = accountsRepository.findById(accountId)
                .orElseThrow(() -> new DataNotFoundException("Account not found with id " + accountId));
        accounts.getCards().forEach(c -> {
            if (c.getNumber().equals(cardsDTORequest.getNumber())) {
                throw new DataAlreadyExistsException("Card already exists");
            }
        });

        Cards cards = mapper.map(cardsDTORequest, Cards.class);

        cards.setAccount(accounts);

        if (cardsDTORequest.getAmmount() == null | cardsDTORequest.getAmmount().compareTo(new BigDecimal(0)) == -1) {
            cards.setAmmount(new BigDecimal(0));
        }

        cards = cardsRepository.save(cards);
        accounts.getCards().add(cards);

        accountsRepository.save(accounts);

        return mapper.map(cards, CardsDTO.class);
    }

    @Override
    public AccountsDTOResponse findAccountById(String id) {
        Accounts accountsModel = accountsRepository.findById(id).orElseThrow(() -> {
            throw new DataNotFoundException("Account not found.");
        });

        return mapper.map(accountsModel, AccountsDTOResponse.class);
    }

//    @Override
//    public void updateAccount(AccountsPatchDTORequest accountsPatchDTORequest, String id) {
//        Accounts accountsDB = accountsRepository.findById(id).orElseThrow(() -> {
//            throw new DataNotFoundException("Account not found.");
//        });
//        Accounts accountsModel = mapper.map(accountsPatchDTORequest, Accounts.class);
//
//        // TODO validar uso de Alias
//
////        accountsRepository.save(accountsDB);
//    }

//    Task 12, 13 e 14
    @Override
    public CardsDTO findAccountCardsById(String accountId, String cardId){

        Accounts accounts = accountsRepository.findById(accountId)
                .orElseThrow(() -> new DataNotFoundException("Account not found with id " + accountId));
        Cards cards = accounts.getCards().stream()
                .filter(c -> c.getCardId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("Card not found with id " + cardId));

        return mapper.map(cards, CardsDTO.class);
    }

    @Override
    public AccountsDTORequest deleteCardOfAccountById(String accountId, String cardId) {
        Accounts accounts = accountsRepository.findById(accountId)
                .orElseThrow(() -> new DataNotFoundException("Account not found with id " + accountId));
        Cards cards = accounts.getCards().stream()
                .filter(c -> c.getCardId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("Card not found with id " + cardId));
        accounts.getCards().remove(cards);
        accountsRepository.save(accounts);
        return null;
    }
}
