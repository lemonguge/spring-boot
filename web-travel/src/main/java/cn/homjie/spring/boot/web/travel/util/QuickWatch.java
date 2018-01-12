package cn.homjie.spring.boot.web.travel.util;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 执行时间输出，若在允许的最小执行时间之内，不输出
 *
 * @author jiehong.jh
 * @date 2018/1/12
 */
@Slf4j
public class QuickWatch {

    private static final String TAB = "  ";

    private static final String INFO = "RT [{}] is {}";
    private static final String INFO_TITLE = "[{}] RT [{}] is {}";

    private int deep;

    private long start;
    private long limit;

    private int order;
    private List<Integer> deeps = new ArrayList<>(15);
    private List<String> titles = new ArrayList<>(15);
    private List<Long> buffer = new ArrayList<>(15);

    public QuickWatch() {
        this(3, TimeUnit.SECONDS);
    }

    public QuickWatch(int min, TimeUnit unit) {
        start = System.currentTimeMillis();
        limit = start + unit.toMillis(min);
    }

    public void mark(String title) {
        mark(title, null, false);
    }

    public void mark(String title, Long exec, boolean force) {
        if (!log.isInfoEnabled()) {
            return;
        }
        deeps.add(deep);
        titles.add(title);
        long now = System.currentTimeMillis();
        if (exec != null) {
            buffer.add(exec);
        } else {
            buffer.add(now - start);
        }
        if (!force && now < limit) {
            return;
        }
        int size = buffer.size();
        for (int i = 0; i < size; i++) {
            int d = deeps.get(i);
            String t = titles.get(i);
            boolean titleExist = t != null;
            if (titleExist) {
                log.info(pretty(INFO_TITLE, d), t, order + i, buffer.get(i));
            } else {
                log.info(pretty(INFO, d), order + i, buffer.get(i));
            }
        }
        order = order + size;
        buffer.clear();
        titles.clear();
        deeps.clear();
    }

    public void deepIn() {
        deep++;
    }

    public void deepOut() {
        deep--;
    }

    public int deep() {
        return deep;
    }

    private String pretty(String template, int d) {
        if (d > 0) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i++ < d) {
                sb.append(TAB);
            }
            return sb.append(template).toString();
        } else {
            return template;
        }
    }
}
