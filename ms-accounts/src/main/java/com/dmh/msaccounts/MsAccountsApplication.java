package com.dmh.msaccounts;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class MsAccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAccountsApplication.class, args);
    }

}
