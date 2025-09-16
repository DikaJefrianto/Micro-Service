package com.dika.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dika.order.model.Order;
import com.dika.order.service.OrderService;
import com.dika.order.vo.ResponseTemplate;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Endpoint untuk ambil semua orders (tanpa detail produk & pelanggan)
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Endpoint untuk ambil detail order lengkap dengan produk & pelanggan
    // URL: http://127.0.0.1:8082/api/orders/1
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate> getOrderWithDetailsById(@PathVariable Long id) {
        ResponseTemplate response = orderService.getOrderWithDetailsById(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint untuk membuat order baru
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    // Endpoint untuk hapus order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}
