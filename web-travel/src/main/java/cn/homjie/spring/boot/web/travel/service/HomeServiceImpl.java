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
@Service
@Slf4j
public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeDao homeDao;

    @Override
    public HomeDO findByNumber(Long number) {
        SleepUtil.hang(log);
        return homeDao.findByNumber(number);
    }
}
