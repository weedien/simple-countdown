package cn.weedien.countdown.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 自定义全局异常
 * <p>
 * 添加错误码属性，基于HTTP状态码
 */
public class BusinessException extends CustomException {

    public BusinessException(HttpStatus code, String message) {
        super(code, message);
    }
}
