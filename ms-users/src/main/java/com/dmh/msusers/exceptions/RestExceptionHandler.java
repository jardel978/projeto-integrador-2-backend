package com.dmh.msusers.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        String URI = String.valueOf(request.getAttribute("org.springframework.web.util.UrlPathHelper.PATH", 0));
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

//    @Override
//    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return super.handleHttpRequestMethodNotSupported(ex, headers, status, request);
//    }

//    @Override
//    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return super.handleMissingPathVariable(ex, headers, status, request);
//    }


    //    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return super.handleExceptionInternal(ex, body, headers, status, request);
//    }

    @ExceptionHandler(DataNotFoundException.class)
    protected ResponseEntity<Object> dataNotFoundException(DataNotFoundException exception, HttpServletRequest request) {
        ApiError error = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .exception(exception.getClass().getSimpleName())
                .path(request.getRequestURI()).build();

        return buildResponseEntity(error);
    }

    @ExceptionHandler(InvalidFieldException.class)
    protected ResponseEntity<Object> invalidFieldException(InvalidFieldException exception,
                                                           HttpServletRequest request) {
        ApiError error = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .exception(exception.getClass().getSimpleName())
                .path(request.getRequestURI()).build();

        return buildResponseEntity(error);
    }

}
