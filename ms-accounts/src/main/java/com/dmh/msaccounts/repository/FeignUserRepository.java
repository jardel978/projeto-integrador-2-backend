package com.dmh.msaccounts.repository;

import com.dmh.msaccounts.configuration.ConfigLoadBalancer;
import com.dmh.msaccounts.model.User;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name= "ms-users", url = "http://localhost:8084")
@LoadBalancerClient(name = "ms-users", configuration = ConfigLoadBalancer.class)
public interface FeignUserRepository {

    @RequestMapping(method = RequestMethod.GET,value = "/users/{id}")
    ResponseEntity<User> findByUserId(@RequestParam Integer userId);


}

