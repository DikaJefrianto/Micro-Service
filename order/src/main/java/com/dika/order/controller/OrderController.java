package com.dika.order.controller; 

import com.dika.order.cqrs.command.CreateOrderCommand;
import com.dika.order.cqrs.query.OrderQueryHandler; 
import com.dika.order.cqrs.query.GetOrderByIdQuery;
import com.dika.order.model.Order;
import com.dika.order.service.OrderService; // Command Handler
import com.dika.order.vo.ResponseTemplate; 

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders") // Endpoint utama untuk Order
@RequiredArgsConstructor
public class OrderController {

    // 1. Dependency untuk COMMANDS (Menulis ke MySQL)
    private final OrderService orderService; 
    
    // 2. Dependency untuk QUERIES (Membaca dari MongoDB)
    private final OrderQueryHandler queryHandler;

    // ---------------------------------------------
    // COMMANDS (WRITE - POST/DELETE)
    // ---------------------------------------------

    // CREATE Order (POST /api/orders)
    @PostMapping 
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderCommand command) {
        // Memanggil Command Service (menulis ke MySQL)
        Order savedOrder = orderService.createOrder(command);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }
    
    // DELETE Order (DELETE /api/orders/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        // Memanggil Command Service (menghapus dari MySQL)
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------------------------------------
    // QUERIES (READ - GET)
    // ---------------------------------------------

    // GET ALL Orders (GET /api/orders)
    @GetMapping 
    public ResponseEntity<List<Order>> getAllOrders() {
        // Memanggil Query Handler (membaca dari MongoDB)
        // Kita butuh method di OrderQueryHandler untuk ini.
        List<Order> orders = queryHandler.handleGetAllOrders(); 
        return ResponseEntity.ok(orders);
    }

    // GET Order By ID (GET /api/orders/{id})
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate> getOrderByIdWithDetails(@PathVariable Long id) {
        
        // Memanggil Query Handler dengan objek Query untuk agregasi detail
        GetOrderByIdQuery query = new GetOrderByIdQuery(id);
        ResponseTemplate response = queryHandler.handleWithDetails(query);
        
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}