package cn.homjie.spring.boot.web.travel.dao;

import cn.homjie.spring.boot.web.travel.entity.HomeDO;
import cn.homjie.spring.boot.web.travel.util.SleepUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiehong.jh
 * @date 2018/1/12
 */
@Repository("homeDao")
@Slf4j
public class HomeDao {

    private List<HomeDO> data = new ArrayList<>();

    @PostConstruct
    private void init() {
        HomeDO o1 = new HomeDO("Spring in Action, Fourth Edition",
            "Spring in Action, Fifth Edition is now available in the Manning Early Access Program. An eBook of this "
                + "older edition is included at no additional cost when you buy the revised edition!",
            1L, LocalDate.of(2016, 4, 1));
        HomeDO o2 = new HomeDO("Maven实战", "Maven——这一Java社区事实标准的项目管理工具，能帮你从琐碎的手工劳动中解脱出来，帮你规范整个组织的构建系统。",
            2L, LocalDate.of(2011, 12, 5));
        HomeDO o3 = new HomeDO("分布式Java应用",
            "本书的基础部分介绍了分布式Java应用的基本实现方式（重点是SOA）、相关的JDK类库和第三方框架，并对JVM"
                + "的基本机制进行了深入解析；实践部分则关注于高性能、高可用和可伸缩系统的构建等。全书文风朴实，并附有大量的代码、数据和图表，比较符合大多数程序员的口味，也非常具有实践指导意义。",
            3L, LocalDate.of(2010, 6, 19));
        data.add(o1);
        data.add(o2);
        data.add(o3);
    }

    public HomeDO findByNumber(Long number) {
        SleepUtil.hang(log);
        if (number != null) {
            for (HomeDO o : data) {
                if (number.equals(o.getNumber())) {
                    return o;
                }
            }
        }
        return null;
    }

    public void logMainSub1() {
        log.info("[logMainSub1]执行耗时：{}", SleepUtil.hang());
    }

    public void logMainSub2() {
        log.info("[logMainSub2]执行耗时：{}", SleepUtil.hang());
    }
}
