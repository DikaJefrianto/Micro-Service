package com.dika.order.repository.query;

import com.dika.order.model.Order;
// 🚨 FIX: Ganti MongoRepository menjadi JpaRepository
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

@Repository
// 🚨 FIX: Extends JpaRepository untuk MySQL
public interface OrderQueryRepository extends JpaRepository<Order, Long> {
}