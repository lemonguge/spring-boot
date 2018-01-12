package cn.homjie.spring.boot.web.travel.service;

import cn.homjie.spring.boot.web.travel.dao.HomeDao;
import cn.homjie.spring.boot.web.travel.entity.HomeDO;
import cn.homjie.spring.boot.web.travel.util.SleepUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jiehong.jh
 * @date 2018/1/12
 */
@Service("homeService")
@Slf4j
public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeDao homeDao;

    @Override
    public HomeDO findByNumber(Long number) {
        SleepUtil.hang(log);
        return homeDao.findByNumber(number);
    }

    @Override
    public void logMain() {
        homeDao.logMainSub1();
        // 本地调用，不走AOP，LogProfiler无法监控
        logInner();
        homeDao.logMainSub2();
        logPrivate();
    }

    @Override
    public void logInner() {
        log.info("[logInner]执行耗时：{}", SleepUtil.hang());
    }

    private void logPrivate() {
        log.info("[logPrivate]执行耗时：{}", SleepUtil.hang());
    }
}
