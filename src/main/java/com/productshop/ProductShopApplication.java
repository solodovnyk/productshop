package com.productshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.productshop")
public class ProductShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductShopApplication.class);
    }
}