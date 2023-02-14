package com.dmh.msgateway.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.util.SerializationUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest resquet = exchange.getRequest();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatusCode(HttpStatus.FORBIDDEN);

        ApiError error = ApiError.builder()
                .status(HttpStatus.FORBIDDEN)
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(denied.getMessage())
                .timestamp(LocalDateTime.now())
                .path(resquet.getPath().value())
                .exception(denied.getClass().toString()).build();

        byte[] bytes = SerializationUtils.serialize(error);
        log.debug("error: " + bytes.toString());
        DataBuffer buffer = response.bufferFactory().wrap(bytes.toString().getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

}
