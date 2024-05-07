package cn.weedien.countdown.model.req;

import lombok.Data;

@Data
public class CountdownCommand {

    private String duration;

    private String remark;

    private String message;

}
