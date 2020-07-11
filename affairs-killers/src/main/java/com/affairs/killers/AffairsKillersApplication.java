package com.affairs.killers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableRedisHttpSession
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AffairsKillersApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static void main(String[] args) {
        SpringApplication.run(AffairsKillersApplication.class, args);
    }

}
