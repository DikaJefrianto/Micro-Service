package com.dika.order.repository.command; 

import com.dika.order.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// FIX: Nama baru untuk menghindari konflik
@Repository
public interface OrderProjectionRepository extends MongoRepository<Order, Long> {
    // Digunakan oleh Projection Service dan Query Handler
}