package com.affairs.course;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableRabbit
@EnableRedisHttpSession
@EnableDiscoveryClient
@MapperScan("com.affairs.course.mapper")
@SpringBootApplication
public class AffairsCourseApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static void main(String[] args) {
        SpringApplication.run(AffairsCourseApplication.class, args);
    }

}