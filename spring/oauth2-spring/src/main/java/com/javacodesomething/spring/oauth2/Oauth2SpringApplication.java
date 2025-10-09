package com.javacodesomething.spring.oauth2;

import com.javacodesomething.spring.oauth2.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class Oauth2SpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2SpringApplication.class, args);
    }

}
