package com.example.productmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ProductManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductManagerApplication.class, args);
    }

}
