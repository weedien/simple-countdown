package cn.weedien.countdown.model;

import lombok.Data;

@Data
public class Result<T> {

    private int code;

    private String message;

    private T data;

    public Result() {
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, T data) {
        this.code = code;
        this.data = data;
    }
}
