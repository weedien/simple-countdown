package cn.weedien.countdown.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 自定义全局异常
 * <p>
 * 添加错误码属性，基于HTTP状态码
 */
public class CustomException extends RuntimeException {

    private final HttpStatus code;

    public CustomException(HttpStatus code, String message) {
        super(message);
        this.code = code;
    }

    public HttpStatus getCode() {
        return code;
    }

}
