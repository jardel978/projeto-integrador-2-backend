package com.dmh.msaccounts.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import java.time.LocalDateTime;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ApiError buildApiError(HttpStatus status, Exception exception, HttpServletRequest request) {
        return ApiError.builder()
                .status(status)
                .error(status.getReasonPhrase())
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .exception(exception.getClass().getSimpleName())
                .path(request.getRequestURI()).build();
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(DataNotFoundException.class)
    protected ResponseEntity<Object> dataNotFoundException(DataNotFoundException exception, HttpServletRequest request) {
        ApiError error = buildApiError(HttpStatus.NOT_FOUND, exception, request);
        return buildResponseEntity(error);
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    protected ResponseEntity<Object> dataAlreadyExistsException(DataAlreadyExistsException exception,
                                                                HttpServletRequest request) {
        ApiError error = buildApiError(HttpStatus.BAD_REQUEST, exception, request);
        return buildResponseEntity(error);
    }

    @ExceptionHandler(GenerateDocumentException.class)
    protected ResponseEntity<Object> generateDocumentException(GenerateDocumentException exception,
                                                               HttpServletRequest request) {
        ApiError error = buildApiError(HttpStatus.BAD_REQUEST, exception, request);
        return buildResponseEntity(error);
    }

    @ExceptionHandler(UserFeignClientException.class)
    protected ResponseEntity<Object> userFeignClientException(UserFeignClientException exception,
                                                               HttpServletRequest request) {
        ApiError error = buildApiError(HttpStatus.BAD_REQUEST, exception, request);
        return buildResponseEntity(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> illegalArgumentException(IllegalArgumentException exception,
                                                              HttpServletRequest request) {
        ApiError error = buildApiError(HttpStatus.BAD_REQUEST, exception, request);
        return buildResponseEntity(error);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    protected ResponseEntity<Object> insufficientFundsException(InsufficientFundsException exception,
                                                                HttpServletRequest request) {
        ApiError error = buildApiError(HttpStatus.BAD_REQUEST, exception, request);
        return buildResponseEntity(error);
    }

    @ExceptionHandler(InvalidFieldException.class)
    protected ResponseEntity<Object> invalidFieldException(InvalidFieldException exception,
                                                           HttpServletRequest request) {
        ApiError error = buildApiError(HttpStatus.BAD_REQUEST, exception, request);
        return buildResponseEntity(error);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> badRequestException(BadRequestException exception,
                                                         HttpServletRequest request) {
        ApiError error = buildApiError(HttpStatus.BAD_REQUEST, exception, request);
        return buildResponseEntity(error);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception,
                                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError error = buildApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception, (HttpServletRequest) request);
        return buildResponseEntity(error);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String uri = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError error = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Malformed JSON request")
                .timestamp(LocalDateTime.now())
                .exception(ex.getClass().getSimpleName())
                .path(uri).build();

        return buildResponseEntity(error);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException exception, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        ApiError error = buildApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception, (HttpServletRequest) request);
        return buildResponseEntity(error);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError error = buildApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception, (HttpServletRequest) request);
        return buildResponseEntity(error);
    }

}
