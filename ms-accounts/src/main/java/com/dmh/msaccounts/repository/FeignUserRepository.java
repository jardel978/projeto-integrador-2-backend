package com.dmh.msaccounts.repository;

import com.dmh.msaccounts.configuration.ConfigLoadBalancer;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-users", url = "MS-USERS:8084/users")
@LoadBalancerClient(name = "ms-users", configuration = ConfigLoadBalancer.class)
public interface FeignUserRepository {

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ResponseEntity<?> findByUserId(@RequestParam String userId);

}
