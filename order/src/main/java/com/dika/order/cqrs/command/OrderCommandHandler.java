package com.dika.order.cqrs.command;

import com.dika.order.config.RabbitMQConfig;
import com.dika.order.model.Order;
// 🚨 FIX: Import Repository MySQL yang benar untuk WRITE
import com.dika.order.repository.query.OrderQueryRepository; 
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class OrderCommandHandler {

    // 🚨 FIX: Inject OrderQueryRepository (MySQL/Write DB)
    private final OrderQueryRepository orderQueryRepository;
    private final RabbitTemplate rabbitTemplate;

    // Menangani perintah untuk membuat Order
    @Transactional("queryTransactionManager") // Transaksi MySQL
    public Order handleCreateOrderCommand(CreateOrderCommand command) {
        
        // 1. Konversi Command menjadi Entitas Order
        Order order = new Order();
        order.setProdukId(command.getProdukId());
        order.setPelangganId(command.getPelangganId());
        order.setJumlah(command.getJumlah());
        
        // Handle konversi tanggal
        try {
            order.setTanggal(LocalDate.parse(command.getTanggal()));
        } catch (Exception e) {
            order.setTanggal(LocalDate.now()); 
        }

        order.setStatus(command.getStatus());
        order.setTotal(command.getTotal());
        
        // 2. Simpan Entitas ke MySQL
        Order savedOrder = orderQueryRepository.save(order); 

        // 3. Terbitkan Event
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME, 
            RabbitMQConfig.ROUTING_KEY_ORDER_CREATED, 
            savedOrder 
        ); 
        
        System.out.println("✅ Command Handled: Order ID " + savedOrder.getId() + " disimpan & Event diterbitkan.");

        return savedOrder;
    }
    
    // Anda bisa menambahkan method untuk handle UpdateOrderCommand dan DeleteOrderCommand di sini
}