package com.itheima.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 小空
 * @create 2022-05-11 10:49
 * @description 瑞吉点餐 Application 入口
 */
@SpringBootApplication
@Slf4j
public class ReggieEnterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieEnterApplication.class, args);
        log.info("Reggie Application Start Success!");
    }
}
