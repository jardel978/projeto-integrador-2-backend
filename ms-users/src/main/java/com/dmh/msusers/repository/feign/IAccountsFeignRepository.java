package com.dmh.msusers.repository.feign;

import com.dmh.msusers.configuration.ConfigLoadBalancer;
import com.dmh.msusers.configuration.feign.OauthFeignConfiguration;
import com.dmh.msusers.model.dto.AccountDTORequest;
import com.dmh.msusers.repository.feign.fallback.AccountsFeignClientFallback;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "ms-accounts", url = "localhost:8085/accounts", configuration = OauthFeignConfiguration.class,
        fallback = AccountsFeignClientFallback.class)
@LoadBalancerClient(name = "ms-accounts", configuration = ConfigLoadBalancer.class)
public interface IAccountsFeignRepository {

    @PostMapping()
    ResponseEntity<Map<String, Object>> createAccount(@RequestBody AccountDTORequest accountDTORequest,
                                                      @RequestParam(name = "first-account") boolean createUserWithAccount);

}
