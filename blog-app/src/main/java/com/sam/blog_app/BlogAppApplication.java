package com.sam.blog_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.sam")
@EnableAsync
public class BlogAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAppApplication.class, args);
    }
}
