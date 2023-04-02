package com.dmh.msusers.configuration;

import com.dmh.msusers.exceptions.CustomAccessDeniedHandler;
import com.dmh.msusers.exceptions.CustomAuthenticationEntryPoint;
import com.dmh.msusers.response.ResponseHandler;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.swagger.v3.oas.models.ExternalDocumentation;

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

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

//    @Configuration
//    @OpenAPIDefinition(
//            info =@Info(
//                    title = "User API",
//                    version = "${api.version}",
//                    contact = @Contact(
//                            name = "Digital Money House - Grupo 3", email = "user-apis@dmh.com", url = "https://www.dmh.com"
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
//
//
////    @SecurityScheme(
////            name = "Bearer Authentication",
////            type = SecuritySchemeType.HTTP,
////            bearerFormat = "JWT",
////            scheme = "bearer"
////    )
//    public class OpenAPI30Configuration {}

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DMH Users Documantation")
                        .description("API desenvolvida para site/app Digital Money House")
                        .version("1.0.0")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }

}
