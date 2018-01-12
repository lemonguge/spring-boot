package cn.homjie.spring.boot.web.travel;

import cn.homjie.spring.boot.web.travel.util.QuickWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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
        try {
            return pjp.proceed();
        } finally {
            long exec = System.currentTimeMillis() - start;
            watch.mark(title, exec, false);
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

}
