package cn.homjie.spring.boot.web.travel.service;

import cn.homjie.spring.boot.web.travel.entity.HomeDO;

/**
 * @author jiehong.jh
 * @date 2018/1/12
 */
public interface HomeService {

    HomeDO findByNumber(Long number);

    void logMain();

    void logInner();
}
