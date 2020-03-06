package com.llb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * @Author llb
 * Date on 2020/3/4
 */
@SpringBootApplication
@MapperScan("com.llb.mapper")
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
