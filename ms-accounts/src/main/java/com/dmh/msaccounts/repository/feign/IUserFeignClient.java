package com.dmh.msaccounts.repository.feign;

import com.dmh.msaccounts.configuration.ConfigLoadBalancer;
import com.dmh.msaccounts.configuration.feign.OauthFeignConfiguration;
import com.dmh.msaccounts.repository.feign.fallback.UsersFeignClientFallback;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "ms-users", url = "localhost:8084/users",
        configuration = OauthFeignConfiguration.class, fallback = UsersFeignClientFallback.class)
@LoadBalancerClient(name = "ms-users", configuration = ConfigLoadBalancer.class)
public interface IUserFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ResponseEntity<Map<String, Object>> findByUserId(@PathVariable("id") String userId);

}

