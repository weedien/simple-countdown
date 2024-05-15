package cn.weedien.countdown.common.log;

import cn.hutool.json.JSONUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogSubject {

    private transient String mode;

    public LogSubject(String mode) {
        this.mode = mode;
    }

    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 操作时间
     */
    private String startTime;

    /**
     * 消耗时间
     */
    private String spendTime;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private Object parameter;

    /**
     * 请求返回的结果
     */
    private Object result;

    /**
     * 状态码
     */
    private int code;

    /**
     * 异常信息
     */
    private String message;

    /**
     * 请求设备信息
     */
    private String device;

    @Override
    public String toString() {
        if (mode.equals("prod")) {
            JSONUtil.toJsonStr(this);
        }
        return JSONUtil.toJsonPrettyStr(this);
    }
}