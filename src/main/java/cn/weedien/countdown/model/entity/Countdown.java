package cn.weedien.countdown.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Countdown {
    private long id;

    private long nid;

    private String queryCode;

    private Date expireAt;

    private String remark;

    private String message;

    private Date createdAt;

    private Date updatedAt;

    private boolean deleted;
}
