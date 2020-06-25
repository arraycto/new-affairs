package com.affairs.teacher;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.affairs.teacher.mapper")
@SpringBootApplication
public class AffairsTeacherApplication {

    public static void main(String[] args) {
        SpringApplication.run(AffairsTeacherApplication.class, args);
    }

}
