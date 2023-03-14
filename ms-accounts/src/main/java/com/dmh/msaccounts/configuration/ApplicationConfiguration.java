package com.dmh.msaccounts.configuration;

import com.dmh.msaccounts.configuration.feign.OauthFeignConfiguration;
import com.dmh.msaccounts.response.ResponseHandler;
import feign.Capability;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.cloud.openfeign.CachingCapability;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfiguration {

    @Bean
    public OauthFeignConfiguration oauthFeignConfiguration(){
        return new OauthFeignConfiguration(registrationRepository);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }

    @Bean
    public ResponseHandler responseHandler() {
        return new ResponseHandler();
    }

//    @Bean
//    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
//        return new CustomAuthenticationEntryPoint();
//    }
//
//    @Bean
//    public CustomAccessDeniedHandler customAccessDeniedHandler() {
//        return new CustomAccessDeniedHandler();
//    }

    @Bean
    @ConditionalOnProperty(value = "spring.cloud.openfeign.cache.enabled", matchIfMissing = true)
    @ConditionalOnBean(CacheInterceptor.class)
    public Capability cachingCapability(CacheInterceptor cacheInterceptor) {
        return new CachingCapability(cacheInterceptor);
    }

}
