package com.sln.ontime;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan(basePackages = {"com.sln.ontime.dao"})
@SpringBootApplication
public class OntimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OntimeApplication.class, args);
    }

}
