package com.nbd.ocp.core.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class StartUpApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartUpApplication.class, args);
    }
}