package com.example.mtls.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
public class GreetingClientApplication {


    public static void main(String[] args) {
        SpringApplication.run(GreetingClientApplication.class, args);
    }
}
