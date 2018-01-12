package cn.homjie.spring.boot.web.travel.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author jiehong.jh
 * @date 2018/1/12
 */
@Slf4j
public class SleepUtil {

    public static void hang(Logger logger) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int time = random.nextInt(800, 1200);
        try {
            logger.info("执行耗时：{}", time);
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static long hang() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long start = System.currentTimeMillis();
        int time = random.nextInt(800, 1200);
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return System.currentTimeMillis() - start;
    }
}
