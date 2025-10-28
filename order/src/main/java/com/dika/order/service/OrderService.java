package com.dika.order.service;

import com.dika.order.cqrs.command.CreateOrderCommand; // Import Command
import com.dika.order.model.Order;
import com.dika.order.repository.query.OrderQueryRepository; // Repository MySQL (WRITE)
import com.dika.order.config.RabbitMQConfig;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderQueryRepository orderQueryRepository; // MySQL Repository
    private final RabbitTemplate rabbitTemplate;

    // 🚨 FIX: Ubah signature method agar menerima CreateOrderCommand
    @Transactional("queryTransactionManager") // Transaksi MySQL (WRITE)
    public Order createOrder(CreateOrderCommand command) {
        
        // 1. Konversi Command (DTO) menjadi Entitas (Order)
        Order order = new Order();
        order.setProdukId(command.getProdukId());
        order.setPelangganId(command.getPelangganId());
        order.setJumlah(command.getJumlah());
        
        // Konversi String tanggal dari Command ke LocalDate di Entitas
        try {
            order.setTanggal(LocalDate.parse(command.getTanggal()));
        } catch (Exception e) {
            // Tangani error jika format tanggal salah, atau gunakan format default
            order.setTanggal(LocalDate.now()); 
        }

        order.setStatus(command.getStatus());
        order.setTotal(command.getTotal());
        
        // 2. Simpan Entitas ke MySQL (Write DB)
        Order savedOrder = orderQueryRepository.save(order); 

        // 3. Terbitkan Event ke RabbitMQ
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME, 
            RabbitMQConfig.ROUTING_KEY_ORDER_CREATED, 
            savedOrder 
        ); 
        
        System.out.println("✅ Order ID " + savedOrder.getId() + " disimpan ke MySQL dan Event diterbitkan.");

        return savedOrder;
    }

    // Metode deleteOrder jika ada
    public void deleteOrder(Long id) {
        // Logika delete (hanya di MySQL)
        orderQueryRepository.deleteById(id);
        // Pertimbangkan menerbitkan OrderDeletedEvent jika diperlukan
    }
}