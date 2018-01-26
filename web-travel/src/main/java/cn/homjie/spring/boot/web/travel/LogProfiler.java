package cn.homjie.spring.boot.web.travel;

import cn.homjie.spring.boot.web.travel.util.QuickWatch;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监控RT超过3秒请求
 *
 * @author jiehong.jh
 * @date 2018/1/12
 */
@Aspect
@Component
public class LogProfiler {

    private ThreadLocal<QuickWatch> threadLocal = new ThreadLocal<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Pointcut("within(cn.homjie.spring.boot.web.travel..*)")
    private void basePackage() {}

    @Pointcut("bean(*Controller) || bean(*Service) || bean(*Dao)")
    private void component() {}

    @Pointcut("basePackage() && component()")
    private void application() {}

    @Around("application()")
    public Object doProfiling(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        String title = signature.getDeclaringTypeName() + "#" + signature.getName();
        QuickWatch watch = start();
        int deep = watch.deep();
        long start = System.currentTimeMillis();
        Object result = null;
        try {
            result = pjp.proceed();
            return result;
        } finally {
            long exec = System.currentTimeMillis() - start;
            watch.mark(title, toJson(pjp.getArgs()), toJson(result), exec, false);
            if (deep == 0) {
                threadLocal.remove();
            } else {
                watch.deepOut();
            }
        }
    }

    private QuickWatch start() {
        QuickWatch watch = threadLocal.get();
        if (watch == null) {
            watch = new QuickWatch();
            threadLocal.set(watch);
        } else {
            watch.deepIn();
        }
        return watch;
    }

    private String toJson(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            return "exception[" + e.getMessage() + "]";
        }
    }

}
