package com.minjae.doongstudy.common.exception;

import com.minjae.doongstudy.domain.member.exception.ExistMemberException;
import com.minjae.doongstudy.domain.member.exception.NoMemberException;
import com.minjae.doongstudy.domain.thing.exception.NoThingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", Objects.requireNonNull(exception.getBindingResult()
                        .getFieldError())
                .getDefaultMessage()
        );
        return ResponseEntity.status(400).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleEnumException(HttpMessageNotReadableException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "유효한 타입이 아닙니다");
        return ResponseEntity.status(400).body(body);
    }

    @ExceptionHandler({
            NoMemberException.class,
            ExistMemberException.class
    })
    public ResponseEntity<Map<String, Object>> handleMemberBadRequestException(RuntimeException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", exception.getMessage());
        return ResponseEntity.status(400).body(body);
    }

    @ExceptionHandler({
            NoThingException.class
    })
    public ResponseEntity<Map<String, Object>> handleThingBadRequestException(RuntimeException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", exception.getMessage());
        return ResponseEntity.status(400).body(body);
    }
}
