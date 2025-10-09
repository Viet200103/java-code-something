package com.example.springbuildingrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class SpringBuildingRestapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBuildingRestapiApplication.class, args);
    }

}
