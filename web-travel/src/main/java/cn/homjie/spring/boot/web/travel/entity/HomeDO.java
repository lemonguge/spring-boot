package cn.homjie.spring.boot.web.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author jiehong.jh
 * @date 2018/1/12
 */
@NoArgsConstructor
@AllArgsConstructor
public class HomeDO implements Serializable {

    private static final long serialVersionUID = -543030231186432946L;

    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String content;
    @Getter
    @Setter
    private Long number;
    @Getter
    @Setter
    private LocalDate time;
}
