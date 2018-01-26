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

    // order title
    private static final String INFO_TITLE = "[{}][{}] rt is {}";
    private static final String INFO_DETAIL = "[{}][{}] rt is [{}], args is {}, result is {}";

    private int deep;

    private long start;
    private long limit;

    private int order;
    private List<DeepInfo> deepInfos = new ArrayList<>(15);

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
        if (ignoreOutput(title, null, null, exec, force)) {
            return;
        }
        int size = deepInfos.size();
        for (int i = 0; i < size; i++) {
            DeepInfo di = deepInfos.get(i);
            log.info(pretty(INFO_TITLE, di.deep), order + i, di.title, di.exec);
        }
        order = order + size;
        deepInfos.clear();
    }

    public void mark(String title, String args, String result, Long exec, boolean force) {
        if (ignoreOutput(title, args, result, exec, force)) {
            return;
        }
        int size = deepInfos.size();
        for (int i = 0; i < size; i++) {
            DeepInfo di = deepInfos.get(i);
            log.info(pretty(INFO_DETAIL, di.deep), order + i, di.title, di.exec, di.args, di.result);
        }
        order = order + size;
        deepInfos.clear();
    }

    private boolean ignoreOutput(String title, String args, String result, Long exec, boolean force) {
        if (!log.isInfoEnabled()) {
            return true;
        }
        DeepInfo deepInfo = new DeepInfo();
        deepInfos.add(deepInfo);
        deepInfo.deep = deep;
        deepInfo.title = title;
        deepInfo.args = args;
        deepInfo.result = result;
        long now = System.currentTimeMillis();
        if (exec != null) {
            deepInfo.exec = exec;
        } else {
            deepInfo.exec = now - start;
        }
        return !force && now < limit;
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

    private static class DeepInfo {
        int deep;
        String title;
        String args;
        String result;
        long exec;
    }
}
