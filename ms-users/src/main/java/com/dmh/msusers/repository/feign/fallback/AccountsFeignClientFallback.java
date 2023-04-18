package com.dmh.msusers.repository.feign.fallback;

import com.dmh.msusers.exceptions.CreateAccountException;
import com.dmh.msusers.model.dto.AccountDTORequest;
import com.dmh.msusers.repository.feign.IAccountsFeignRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@NoArgsConstructor
@Component
public class AccountsFeignClientFallback implements IAccountsFeignRepository {

    @Override
    public ResponseEntity<Map<String, Object>> createAccount(AccountDTORequest accountDTORequest, boolean createUserWithAccount) {
        throw new CreateAccountException("User created but cannot create account. Try again in account service.");
    }

}
