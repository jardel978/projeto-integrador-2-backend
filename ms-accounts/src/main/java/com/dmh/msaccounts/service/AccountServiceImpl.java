package com.dmh.msaccounts.service;


import com.dmh.msaccounts.exception.DataNotFoundException;
import com.dmh.msaccounts.model.Accounts;
import com.dmh.msaccounts.model.Cards;
import com.dmh.msaccounts.model.dto.AccountsDTORequest;
import com.dmh.msaccounts.model.dto.AccountsDTOResponse;
import com.dmh.msaccounts.model.dto.AccountsPatchDTORequest;
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
        return mapper.map(accountsRepository.save(accountsModel), AccountsDTOResponse.class);
    }

    @Override
    public AccountsDTOResponse findAccountById(String id) {
        Accounts accountsModel = accountsRepository.findById(id).orElseThrow(() -> {
            throw new DataNotFoundException("Account not found.");
        });

        return mapper.map(accountsModel, AccountsDTOResponse.class);
    }

    @Override
    public void updateAccount(AccountsPatchDTORequest accountsPatchDTORequest, String id) {
        Accounts accountsDB = accountsRepository.findById(id).orElseThrow(() -> {
            throw new DataNotFoundException("Account not found.");
        });
        Accounts accountsModel = mapper.map(accountsPatchDTORequest, Accounts.class);

        // TODO validar uso de Alias

//        accountsRepository.save(accountsDB);
    }

//    Task 12, 13 e 14
    @Override
    public AccountsDTORequest findAccountCardsById(String accountId, String cardId){

//        TODO criar findAccountCardsById no Repository

        AccountsDTORequest card = accountsRepository.findAccountCardsById(accountId, cardId).orElseThrow(() -> {
            throw new DataNotFoundException("Card not found");
        });
        return mapper.map(card, AccountsDTORequest.class);
    }

    @Override
    public AccountsDTORequest deleteCardOfAccountById(String accountId, String cardId) {
        Accounts accounts = accountsRepository.findById(accountId)
                .orElseThrow(() -> new DataNotFoundException("Account not found with id " + accountId));
        Cards cards = accounts.getCards().stream()
                .filter(c -> c.getId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("Card not found with id " + cardId));
        accounts.getCards().remove(cards);
        accountsRepository.save(accounts);
        return null;
    }
}
