package com.dmh.msaccounts.repository;

import com.dmh.msaccounts.configuration.ConfigLoadBalancer;
import com.dmh.msaccounts.configuration.feign.OauthFeignConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-users", url = "localhost:8090/users", configuration = OauthFeignConfiguration.class)
@LoadBalancerClient(name = "ms-users", configuration = ConfigLoadBalancer.class)
public interface FeignUserRepository {

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ResponseEntity<?> findByUserId(@PathVariable("id") String userId);

}

