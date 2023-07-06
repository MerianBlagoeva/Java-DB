package com.softuni.springdataintroexercises.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Scanner sc() {
        return new Scanner(System.in);
    }
}
