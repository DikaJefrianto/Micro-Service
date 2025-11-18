package com.dika.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate; // Tipe data yang lebih aman

@Data
// 1. ANOTASI JPA (Untuk MySQL - Write DB)
@Table(name = "orders") // JPA Annotation
@Entity
public class Order implements Serializable { 
    
    private static final long serialVersionUID = 1L; 

    // ID berfungsi untuk JPA dan MongoDB
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long produkId; // Changed to Long for consistency
    private Long pelangganId; // Changed to Long for consistency
    private Integer jumlah; // Changed to Integer for consistency
    
    // Menggunakan tipe data objek untuk konsistensi yang lebih baik antar database
    private LocalDate tanggal; 
    
    private String status;
    private Double total; // Changed to Double for consistency
}