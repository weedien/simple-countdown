package cn.weedien.countdown.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.query-code")
public class QueryCodeProperties {

    private int queryCodeLen = 6;

    private int codeRetainTime = 60;

    private String recyclePlan = "0 0 0 * * ?";

}