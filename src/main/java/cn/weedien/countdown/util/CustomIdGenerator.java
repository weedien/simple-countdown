package cn.weedien.countdown.util;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class CustomIdGenerator implements InitializingBean {

    public long nextId() {
        return YitIdHelper.nextId();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        IdGeneratorOptions options = new IdGeneratorOptions((short) 1);
        YitIdHelper.setIdGenerator(options);
    }
}
