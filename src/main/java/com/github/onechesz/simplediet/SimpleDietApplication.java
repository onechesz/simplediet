package com.github.onechesz.simplediet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SimpleDietApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleDietApplication.class, args);
    }

}
