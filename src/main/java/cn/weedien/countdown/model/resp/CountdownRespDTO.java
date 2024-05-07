package cn.weedien.countdown.model.resp;

import lombok.Data;

import java.util.Date;

@Data
public class CountdownRespDTO {

    private String queryCode;

    private Date createdAt;

    private Date expireAt;
}
