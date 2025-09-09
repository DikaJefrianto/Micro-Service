package com.dika.produk_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class ProdukServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProdukServiceApplication.class, args);
    }
}