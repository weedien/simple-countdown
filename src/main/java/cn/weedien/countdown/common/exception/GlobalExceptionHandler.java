package cn.weedien.countdown.common.exception;

import cn.weedien.countdown.model.Result;
import cn.weedien.countdown.uitl.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> handleException(Exception ex) {
        // 请求参数异常
        if (ex instanceof IllegalArgumentException || ex instanceof IllegalStateException || ex instanceof HttpMessageConversionException) {
            return new ResponseEntity<>(Results.failure(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
        // 自定义异常
        if (ex instanceof CustomException customException) {
            return new ResponseEntity<>(Results.failure(customException.getCode().value(), customException.getMessage()), customException.getCode());
        }
        // 未知异常
        return new ResponseEntity<>(Results.failure(HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}