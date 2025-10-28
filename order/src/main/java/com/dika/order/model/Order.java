package com.dika.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate; // Tipe data yang lebih aman

import org.springframework.data.mongodb.core.mapping.Document; // MongoDB Annotation

@Data
// 1. ANOTASI JPA (Untuk MySQL - Write DB)
@Table(name = "orders")
@Entity
// 2. ANOTASI MONGODB (Untuk MongoDB - Read DB/Projection)
@Document(collection = "order_projections") 
// 3. INTERFACE SERIALIZATION (Untuk RabbitMQ)
public class Order implements Serializable { 
    
    private static final long serialVersionUID = 1L; 

    // ID berfungsi untuk JPA dan MongoDB
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private long produkId;
    private long pelangganId;
    private int jumlah;
    
    // Menggunakan tipe data objek untuk konsistensi yang lebih baik antar database
    private LocalDate tanggal; 
    
    private String status;
    private double total;
}