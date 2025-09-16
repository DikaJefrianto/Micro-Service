package com.dika.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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
    private DiscoveryClient discoveryClient;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    // ✅ Simpan order
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    // ✅ Ambil semua order tanpa detail
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // ✅ Ambil 1 order by ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // ✅ Ambil 1 order lengkap dengan produk & pelanggan
    public ResponseTemplate getOrderWithDetailsById(Long id) {
        Order order = getOrderById(id);
        if (order == null) {
            return null;
        }

        // --- Produk ---
        List<ServiceInstance> produkInstances = discoveryClient.getInstances("produk-service");
        if (produkInstances.isEmpty()) {
            throw new IllegalStateException("Service PRODUK tidak tersedia");
        }
        Produk produk = restTemplate.getForObject(
                produkInstances.get(0).getUri() + "/api/produk/" + order.getProdukId(),
                Produk.class);

        // --- Pelanggan ---
        List<ServiceInstance> pelangganInstances = discoveryClient.getInstances("PELANGGAN");
        if (pelangganInstances.isEmpty()) {
            throw new IllegalStateException("Service PELANGGAN tidak tersedia");
        }
        Pelanggan pelanggan = restTemplate.getForObject(
                pelangganInstances.get(0).getUri() + "/api/pelanggan/" + order.getPelangganId(),
                Pelanggan.class);

        // --- Bungkus ke ResponseTemplate ---
        ResponseTemplate response = new ResponseTemplate();
        response.setOrder(order);
        response.setProduk(produk);
        response.setPelanggan(pelanggan);

        return response;
    }

    // ✅ Ambil semua order lengkap dengan produk & pelanggan
    public List<ResponseTemplate> getAllOrdersWithDetails() {
        List<ResponseTemplate> responseList = new ArrayList<>();
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            ResponseTemplate response = getOrderWithDetailsById(order.getId());
            if (response != null) {
                responseList.add(response);
            }
        }

        return responseList;
    }

    // ✅ Hapus order
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
