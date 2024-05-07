package cn.weedien.countdown.model.resp;

import lombok.Data;

import java.util.Date;

@Data
public class CountdownQueryRespDTO {
    private Long id;

    private String queryCode;

    private String duration;

    private String remaining;

    private String passed;

    private Date createdAt;

    private Date expireAt;

    private String remark;

    private String message;
}
