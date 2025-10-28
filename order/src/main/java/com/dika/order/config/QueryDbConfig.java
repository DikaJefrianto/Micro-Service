package com.dika.order.config;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map; 

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan; // Diperlukan untuk Filter
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType; // Diperlukan untuk Filter
import org.springframework.context.annotation.Primary; 
import org.springframework.data.jpa.repository.JpaRepository; // Diperlukan untuk Filter
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.dika.order.repository.query", // Batasan paket
    entityManagerFactoryRef = "queryEntityManagerFactory",
    transactionManagerRef = "queryTransactionManager",
    // 🚨 FIX UTAMA: Filter hanya untuk JpaRepository. Ini mencegah JPA melihat MongoRepository.
    includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, 
        value = JpaRepository.class
    )
)
public class QueryDbConfig {
    
    // --- 1. Data Source Properties ---
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.query")
    public DataSourceProperties queryDataSourceProperties() {
        return new DataSourceProperties();
    }

    // --- 2. Data Source ---
    @Primary
    @Bean(name = "queryDataSource")
    public DataSource queryDataSource() {
        return queryDataSourceProperties().initializeDataSourceBuilder().build();
    }

    // --- 3. Entity Manager Factory ---
    @Primary
    @Bean(name = "queryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean queryEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "none"); 
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

        return builder
            .dataSource(queryDataSource())
            .packages("com.dika.order.model") 
            .persistenceUnit("query")
            .properties(properties) 
            .build();
    }

    // --- 4. Transaction Manager ---
    @Primary
    @Bean(name = "queryTransactionManager")
    public JpaTransactionManager queryTransactionManager(
        @Qualifier("queryEntityManagerFactory") LocalContainerEntityManagerFactoryBean queryEntityManagerFactory) {
        return new JpaTransactionManager(queryEntityManagerFactory.getObject());
    }
}