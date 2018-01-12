package cn.homjie.spring.boot.web.travel.controller;

import cn.homjie.spring.boot.web.travel.entity.HomeDO;
import cn.homjie.spring.boot.web.travel.service.HomeService;
import cn.homjie.spring.boot.web.travel.util.SleepUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiehong.jh
 * @date 2018/1/12
 */
@Slf4j
@RestController
@RequestMapping(value = "/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping
    public String home() {
        return "Welcome!";
    }

    @GetMapping("/article/{num}")
    public HomeDO home(@PathVariable Long num) {
        SleepUtil.hang(log);
        return homeService.findByNumber(num);
    }
}
