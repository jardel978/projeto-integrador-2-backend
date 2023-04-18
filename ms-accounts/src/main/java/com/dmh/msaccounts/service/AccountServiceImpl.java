package com.dmh.msaccounts.service;


import com.dmh.msaccounts.exception.DataAlreadyExistsException;
import com.dmh.msaccounts.exception.DataNotFoundException;
import com.dmh.msaccounts.model.Accounts;
import com.dmh.msaccounts.model.Cards;
import com.dmh.msaccounts.model.dto.CardsDTO;
import com.dmh.msaccounts.model.dto.requests.AccountsDTORequest;
import com.dmh.msaccounts.model.dto.requests.CardsDTORequest;
import com.dmh.msaccounts.model.dto.responses.AccountsDTOResponse;
import com.dmh.msaccounts.repository.feign.IUserFeignClient;
import com.dmh.msaccounts.repository.IAccountsRepository;
import com.dmh.msaccounts.repository.ICardsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IUserFeignClient iUserFeignClient;

    @Autowired
    private IAccountsRepository accountsRepository;

    @Autowired
    private ICardsRepository cardsRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public AccountsDTOResponse createAccount(AccountsDTORequest accountsDTORequest, boolean createUserWithAccount) {

        Accounts accountsModel = Accounts.builder()
                .userId(accountsDTORequest.getUserId()).build();

        if (!createUserWithAccount) {
            ResponseEntity<Map<String, Object>> response = iUserFeignClient.findByUserId(accountsDTORequest.getUserId());
            log.info("response: " + response.getBody().toString());
            if (response.getBody().containsKey("error")) {
                throw new DataNotFoundException("User not found.");
            }
        }
        UUID uuid = UUID.nameUUIDFromBytes((accountsModel.getUserId() + LocalDateTime.now().toString()).getBytes(StandardCharsets.UTF_8));
        Long number = uuid.getMostSignificantBits();
        if (number < 0) {
            number = number * -1;
        }
        String accountNumber = Long.toString(number).substring(0, 5);
        accountsModel.setAccount(accountNumber);
        accountsModel.setAmmount(new BigDecimal(0));

        if (accountsModel.getCards() != null && !accountsModel.getCards().isEmpty()) {
            accountsModel.getCards().stream().map(cards -> cardsRepository.save(cards)).collect(Collectors.toSet());
        }

        return mapper.map(accountsRepository.save(accountsModel), AccountsDTOResponse.class);
    }

    @Override
    public CardsDTO createCardByAccount(Long accountId, CardsDTORequest cardsDTORequest) {
        Accounts account = accountsRepository.findById(accountId)
                .orElseThrow(() -> new DataNotFoundException("Account not found with id " + accountId));

        if (!account.getCards().isEmpty())
            account.getCards().forEach(c -> {
                log.info("tem cartão");
                if (c.getNumber().equals(cardsDTORequest.getNumber())) {
                    throw new DataAlreadyExistsException("Card already exists");
                }
            });

        Cards card = mapper.map(cardsDTORequest, Cards.class);

        card.setAccount(account);

        if (cardsDTORequest.getAmmount() == null | cardsDTORequest.getAmmount().compareTo(new BigDecimal(0)) == -1) {
            card.setAmmount(new BigDecimal(0));
        }

        card = cardsRepository.saveAndFlush(card);
        account.getCards().add(card);

        accountsRepository.save(account);

        return mapper.map(card, CardsDTO.class);
    }

    @Override
    public AccountsDTOResponse findAccountById(Long id) {
        Accounts accountsModel = accountsRepository.findById(id).orElseThrow(() -> {
            throw new DataNotFoundException("Account not found.");
        });

        return mapper.map(accountsModel, AccountsDTOResponse.class);
    }

    @Override
    public CardsDTO findAccountCardsById(Long accountId, Long cardId) {
        Accounts accounts = accountsRepository.findById(accountId)
                .orElseThrow(() -> new DataNotFoundException("Account not found with id " + accountId));
        Cards cards = accounts.getCards().stream()
                .filter(c -> c.getId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("Card not found with id " + cardId));

        return mapper.map(cards, CardsDTO.class);
    }

    @Override
    public AccountsDTORequest deleteCardOfAccountById(Long accountId, Long cardId) {
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
