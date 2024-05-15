package cn.weedien.countdown.common.exception;

import cn.weedien.countdown.common.log.LogSubject;
import cn.weedien.countdown.model.Result;
import cn.weedien.countdown.util.HttpContextUtil;
import cn.weedien.countdown.util.Results;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@Order(2)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.profiles.active}")
    private String active;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<?>> handleBusinessException(BusinessException ex) {
        // 业务异常在GlobalLogAspect中已经记录日志，这里不再记录
        // 保证所有经过Controller处理的异常都是BusinessException
        return new ResponseEntity<>(Results.failure(ex.getCode().value(), ex.getMessage()), ex.getCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleException(Exception ex) {
        if (ex instanceof NoHandlerFoundException) {
            return handleException(ex, HttpStatus.NOT_FOUND);
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            return handleException(ex, HttpStatus.METHOD_NOT_ALLOWED);
        } else if (ex instanceof IllegalArgumentException || ex instanceof IllegalStateException || ex instanceof HttpMessageConversionException) {
            return handleException(ex, HttpStatus.BAD_REQUEST);
        } else if (ex instanceof CustomException customException) {
            // 非业务类型的其他自定义错误，权限认证等
            return handleException(customException, customException.getCode());
        }
        // 未知异常，不能直接将ex中的信息返回，因为其中可能包含了业务敏感信息
        logException(ex, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(Results.failure(HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Result<?>> handleException(Exception ex, HttpStatus status) {
        logException(ex, status.value());
        return new ResponseEntity<>(Results.failure(status.value(), ex.getMessage()), status);
    }

    private void logException(Exception ex, int code) {
        LogSubject logSubject = new LogSubject(active);
        logSubject.setCode(code);
        // 记录异常日志
        HttpServletRequest request = HttpContextUtil.getRequest();
        // 请求链接
        logSubject.setUrl(request.getRequestURL().toString());
        // 请求方法GET,POST等
        logSubject.setMethod(request.getMethod());
        // 请求设备信息
        logSubject.setDevice(HttpContextUtil.getDevice());
        // 请求地址
        logSubject.setIp(HttpContextUtil.getIpAddr());
        // 异常信息
        logSubject.setMessage(ex.getMessage());
        // 记录日志
        log.error(logSubject.toString());
    }
}