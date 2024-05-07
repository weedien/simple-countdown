package cn.weedien.countdown.service.impl;

import cn.weedien.countdown.config.QueryCodeProperties;
import cn.weedien.countdown.dao.CountdownDao;
import cn.weedien.countdown.service.QueryCodeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class QueryCodeServiceImpl implements QueryCodeService, InitializingBean {

    @Resource
    private QueryCodeProperties queryCodeProperties;

    @Resource
    private CountdownDao countdownDao;

    private static final Map<String, Boolean> codeCache = new ConcurrentHashMap<>();
    private static final Random randSource = new Random();


    public String genQueryCode() {
        int len = queryCodeProperties.getQueryCodeLen();

        for (int i = 0; i < 10; i++) {
            String code = genCode(len);
            if (!codeCache.containsKey(code)) {
                codeCache.put(code, true);
                return code;
            }
        }

        while (true) {
            String code = genCode(len + 2);
            if (!codeCache.containsKey(code)) {
                codeCache.put(code, true);
                return code;
            }
        }
    }

    public boolean isValidQueryCode(String code) {
        return codeCache.containsKey(code);
    }

    private static String genCode(int n) {
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int digit = randSource.nextInt(10);
            number.append(digit);
        }
        return number.toString();
    }

    @Scheduled(cron = "${app.query-code.recycle-plan}")
    public void queryCodeRecycleTask() {
        int codeRetainTime = queryCodeProperties.getCodeRetainTime();

        LocalDateTime start = LocalDateTime.now();
        log.info("Executing QueryCodeRecycleTask");
        log.info("codeCache size before recycle: {}", codeCache.size());

        LocalDateTime tempTime = LocalDateTime.now().minusMinutes(codeRetainTime);
        countdownDao.listExpiredQueryCodes(Timestamp.valueOf(tempTime)).forEach(codeCache::remove);

        log.info("codeCache size after recycle: {}", codeCache.size());
        log.info("QueryCodeRecycleTask finished in {}", Duration.between(start, LocalDateTime.now()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        countdownDao.listValidQueryCodes().forEach(code -> codeCache.put(code, true));
    }
}
