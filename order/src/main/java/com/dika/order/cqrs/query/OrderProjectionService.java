package com.dika.order.cqrs.query;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 

import com.dika.order.config.RabbitMQConfig;
import com.dika.order.model.Order;
import com.dika.order.repository.command.OrderProjectionRepository; // Repository MongoDB

@Service
public class OrderProjectionService {

    @Autowired 
    private OrderProjectionRepository orderProjectionRepository; 

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME_PROJECTION) 
    public void handleOrderCreatedEvent(Order order) {
        
        System.out.println("DEBUG: Menerima Event Order ID: " + order.getId() + ". Memproyeksikan ke MongoDB...");
        
        try {
            // MongoDB save (upsert)
            orderProjectionRepository.save(order); 
            
            System.out.println("✅ Proyeksi Order ID " + order.getId() + " berhasil di MongoDB.");

        } catch (Exception e) {
            System.err.println("❌ GAGAL TOTAL: Error saat menyimpan Order ID " + order.getId() + " ke MongoDB: " + e.getMessage());
            e.printStackTrace();
            
            // Wajib: Lempar RuntimeException untuk RabbitMQ Re-queue
            throw new RuntimeException("Gagal Proyeksi ke MongoDB. Pesan akan dicoba ulang.", e); 
        }
    }
}