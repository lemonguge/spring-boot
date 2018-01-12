package cn.homjie.spring.boot.web.travel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 执行时间输出，若在允许的最小执行时间之内，不输出
 *
 * @author jiehong.jh
 * @date 2018/1/12
 */
public class QuickWatch {

    private static final String INFO = "RT [{}] is {}";
    private static final String INFO_TITLE = "[{}] RT [{}] is {}";
    private static final Logger DEFAULT = LoggerFactory.getLogger("QuickWatch");

    private String title;
    private boolean titleExist;

    private Logger logger;

    private long start;
    private long limit;

    private int before;
    private List<Long> buffer = new ArrayList<>(8);

    public QuickWatch() {
        this(null, null, 3, TimeUnit.SECONDS);
    }

    public QuickWatch(String title) {
        this(title, null, 3, TimeUnit.SECONDS);
    }

    public QuickWatch(int min, TimeUnit unit) {
        this(null, null, min, unit);
    }

    public QuickWatch(Logger logger, int min, TimeUnit unit) {
        this(null, logger, min, unit);
    }

    public QuickWatch(String title, int min, TimeUnit unit) {
        this(title, null, min, unit);
    }

    public QuickWatch(String title, Logger logger, int min, TimeUnit unit) {
        this.title = title;
        titleExist = title != null;
        this.logger = logger == null ? DEFAULT : logger;
        start = System.currentTimeMillis();
        limit = start + unit.toMillis(min);
    }

    /**
     * 打标记，如果在最小时间限制内则不输出
     */
    public void mark() {
        mark(false);
    }

    public void mark(boolean force) {
        if (!logger.isInfoEnabled()) {
            return;
        }
        long now = System.currentTimeMillis();
        buffer.add(now - start);
        if (!force && now < limit) {
            return;
        }
        int size = buffer.size();
        for (int i = 0; i < size; i++) {
            if (titleExist) {
                logger.info(INFO_TITLE, title, before + i, buffer.get(i));
            } else {
                logger.info(INFO, before + i, buffer.get(i));
            }
        }
        before = before + size;
        buffer.clear();
    }
}
