package com.affairs.teacher;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableRedisHttpSession
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.affairs.teacher.mapper")
@SpringBootApplication
public class AffairsTeacherApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static void main(String[] args) {
        SpringApplication.run(AffairsTeacherApplication.class, args);
    }

}
