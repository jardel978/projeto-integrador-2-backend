package com.dmh.msaccounts.repository.feign.fallback;

import com.dmh.msaccounts.exception.UserFeignClientException;
import com.dmh.msaccounts.repository.feign.IUserFeignClient;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@NoArgsConstructor
@Component
public class UsersFeignClientFallback implements IUserFeignClient {

    @Override
    public ResponseEntity<Map<String, Object>> findByUserId(String userId) {
        throw new UserFeignClientException("Cannot find user.");
    }

}
