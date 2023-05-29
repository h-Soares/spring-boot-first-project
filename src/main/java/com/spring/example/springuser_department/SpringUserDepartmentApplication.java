package com.spring.example.springuser_department;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class) //just to use BCrypt
public class SpringUserDepartmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringUserDepartmentApplication.class, args);
    }
}