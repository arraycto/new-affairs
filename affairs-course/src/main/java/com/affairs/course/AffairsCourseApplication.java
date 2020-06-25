package com.affairs.course;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.affairs.course.mapper")
@SpringBootApplication
public class AffairsCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(AffairsCourseApplication.class, args);
    }

}
