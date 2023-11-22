package com.sponet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SponetApplication {

    public static void main(String[] args) {
        SpringApplication.run(SponetApplication.class, args);
    }

}
