package com.affairs.student;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan("com.affairs.student.mapper")
@SpringBootApplication
public class AffairsStudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AffairsStudentApplication.class, args);
    }

}
