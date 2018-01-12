package cn.homjie.spring.boot.web.travel.util;

import org.slf4j.Logger;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author jiehong.jh
 * @date 2018/1/12
 */
public class SleepUtil {

    public static void hang(Logger logger) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int time = random.nextInt(100, 600);
        try {
            logger.info("执行耗时：{}", time);
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
