package com.dika.order.service;

import com.dika.order.model.Order;
import com.dika.order.repository.OrderRepository;
import com.dika.order.vo.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public ResponseTemplate getOrderByIdWithDetails(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            ResponseTemplate response = new ResponseTemplate();
            response.setOrder(order.get());
            // Populate additional details if needed
            return response;
        }
        return null;
    }
}
