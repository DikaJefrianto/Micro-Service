package com.dika.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.MongoRepository; // Diperlukan untuk Filter
import org.springframework.context.annotation.ComponentScan; // Diperlukan untuk Filter
import org.springframework.context.annotation.FilterType; // Diperlukan untuk Filter

@Configuration
@EnableMongoRepositories(
    basePackages = "com.dika.order.repository.command", // Batasan paket
    // 🚨 FIX UTAMA: Filter hanya untuk MongoRepository. Ini mencegah MongoDB melihat JpaRepository.
    includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, 
        value = MongoRepository.class
    )
)
public class MongoConfig {
    // Kelas ini menyelesaikan masalah konflik scanning.
}