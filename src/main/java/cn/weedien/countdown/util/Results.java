package cn.weedien.countdown.util;

import cn.weedien.countdown.model.Result;
import org.springframework.http.HttpStatus;

public class Results {

    public static <T> Result<T> success(T data) {
        return new Result<>(200, data);
    }

    public static <T> Result<T> success() {
        return new Result<>(200, "success");
    }

    public static <T> Result<T> failure(int code, String message) {
        return new Result<>(code, message);
    }

    public static  <T> Result<T> failure(HttpStatus status) {
        return new Result<>(status.value(), status.getReasonPhrase());
    }

    public static <T> Result<T> failure(int code) {
        return new Result<>(code, "error");
    }

}
