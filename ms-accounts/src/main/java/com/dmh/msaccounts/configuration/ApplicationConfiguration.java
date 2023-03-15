package com.dmh.msaccounts.configuration;

import com.dmh.msaccounts.response.ResponseHandler;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfiguration {

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

//    @Bean
//    @ConditionalOnProperty(value = "spring.cloud.openfeign.cache.enabled", matchIfMissing = true)
//    @ConditionalOnBean(CacheInterceptor.class)
//    public Capability cachingCapability(CacheInterceptor cacheInterceptor) {
//        return new CachingCapability(cacheInterceptor);
//    }

//    @Configuration
//    @OpenAPIDefinition(
//            info =@Info(
//                    title = "User API",
//                    version = "${api.version}",
//                    contact = @Contact(
//                            name = "Baeldung", email = "user-apis@baeldung.com", url = "https://www.baeldung.com"
//                    ),
//                    license = @License(
//                            name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
//                    ),
//                    termsOfService = "${tos.uri}",
//                    description = "${api.description}"
//            ),
//            servers = @Server(
//                    url = "${api.server.url}",
//                    description = "Production"
//            )
//    )
//    public class OpenAPISecurityConfiguration {}

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("DMH Accounts Documantation")
                        .description("API desenvolvida para site/app Digital Money House")
                        .version("1.0.0")
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }

}
