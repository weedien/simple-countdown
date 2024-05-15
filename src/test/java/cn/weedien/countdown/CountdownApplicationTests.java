package cn.weedien.countdown;

import cn.weedien.countdown.dao.CountdownDao;
import cn.weedien.countdown.util.CustomIdGenerator;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.DriverManager;

@Slf4j
@SpringBootTest
class CountdownApplicationTests {

    @Resource
    private CustomIdGenerator idGenerator;

    @Test
    void IdGeneratorTest() {
        log.info("id: {}", idGenerator.nextId());
    }

    @Resource
    private CountdownDao countdownDao;

    @Test
    void CountdownDaoTest() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> log.info(driver.getClass().getName()));
        log.info(String.valueOf(countdownDao.getClass().getClassLoader()));
        log.info("countdown: {}", countdownDao.selectById(1));
    }

}
