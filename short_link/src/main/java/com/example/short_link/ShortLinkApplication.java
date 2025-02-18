package com.example.short_link;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication(exclude = {
//		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
//		org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class })

@SpringBootApplication
@EnableScheduling
public class ShortLinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortLinkApplication.class, args);
    }

}
