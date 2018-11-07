package com.banzaicloud.spotguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class UserApplication {

    @PostConstruct
    private void postConstruct() {
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
