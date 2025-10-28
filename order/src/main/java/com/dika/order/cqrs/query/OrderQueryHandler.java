package com.dika.order.cqrs.query;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.dika.order.model.Order;
// Repository MongoDB untuk Query
import com.dika.order.repository.command.OrderProjectionRepository; 
import com.dika.order.vo.ResponseTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderQueryHandler {

    private final OrderProjectionRepository orderProjectionRepository; 
    
    // WARN: restTemplate tidak digunakan. Harusnya digunakan untuk memanggil service lain.
    // Kita biarkan saja untuk sementara, tetapi jika tidak digunakan, hapus saja.
    private final RestTemplate restTemplate = new RestTemplate(); 

    // ... URL service lain ...

    public ResponseTemplate handleWithDetails(GetOrderByIdQuery query) {
        
        // 1. Membaca dari MongoDB
        Optional<Order> orderOpt = orderProjectionRepository.findById(query.getId());
        if (orderOpt.isEmpty()) {
            System.out.println("DEBUG: Order ID " + query.getId() + " tidak ditemukan di MongoDB.");
            return null;
        }

        Order order = orderOpt.get();
        
        // 2. Logika Pemanggilan Service Eksternal (Anda perlu menambahkan ini)
        // Contoh:
        // Produk produk = restTemplate.getForObject(PRODUCT_SERVICE_URL + order.getProdukId(), Produk.class);
        // Pelanggan pelanggan = restTemplate.getForObject(PELANGGAN_SERVICE_URL + order.getPelangganId(), Pelanggan.class);
        
        // 🚨 FIX: Deklarasi dan Inisialisasi variabel response
        ResponseTemplate response = new ResponseTemplate();
        response.setOrder(order);
        
        // Asumsi Anda memiliki setter untuk Produk dan Pelanggan di ResponseTemplate
        // response.setProduk(produk); 
        // response.setPelanggan(pelanggan); 

        return response; // Baris 35 sekarang sudah valid
    }

    public List<Order> handleGetAllOrders() {
        return orderProjectionRepository.findAll();
    }
}