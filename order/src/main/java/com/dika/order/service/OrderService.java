package com.dika.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dika.order.model.Order;
import com.dika.order.repository.OrderRepository;
import com.dika.order.vo.Pelanggan;
import com.dika.order.vo.Produk;
import com.dika.order.vo.ResponseTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElse(null);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // Ambil detail order lengkap
    public ResponseTemplate getOrderWithDetails(Long orderId) {
        ResponseTemplate vo = new ResponseTemplate();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order tidak ditemukan"));

        // Panggil produk-service
        Produk produk = restTemplate.getForObject(
                "http://localhost:8081/api/produk/" + order.getProdukId(),
                Produk.class
        );

        // Panggil pelanggan-service
        Pelanggan pelanggan = restTemplate.getForObject(
                "http://localhost:8082/api/pelanggan/" + order.getPelangganId(),
                Pelanggan.class
        );

        vo.setOrder(order);
        vo.setProduk(produk);
        vo.setPelanggan(pelanggan);

        return vo;
    }

    
}
