package com.dmh.msaccounts.configuration;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class ConfigLoadBalancer {

    @Bean
    ReactorLoadBalancer<ServiceInstance> randomLoadBalancer
            (Environment environment, LoadBalancerClientFactory loadBalancerClientFactory) {
        String nome = environment.getProperty(loadBalancerClientFactory.PROPERTY_NAME);
        return new RandomLoadBalancer(
                loadBalancerClientFactory.getLazyProvider(nome, ServiceInstanceListSupplier.class), nome
        );

    }

}