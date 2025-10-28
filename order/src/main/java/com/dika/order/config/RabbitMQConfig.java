package com.dika.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter; // BARU
import org.springframework.amqp.support.converter.MessageConverter; // BARU
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Nama Exchange dan Queue yang akan digunakan
    public static final String EXCHANGE_NAME = "orderExchange";
    public static final String ROUTING_KEY_ORDER_CREATED = "order.created";
    public static final String QUEUE_NAME_PROJECTION = "orderQueryQueue";

    // 🚨 KUNCI FIX: Definisikan Konverter Pesan JSON
    @Bean
    public MessageConverter jsonMessageConverter() {
        // Ini memberi tahu RabbitTemplate untuk mengkonversi objek Java menjadi JSON (String) saat mengirim
        return new Jackson2JsonMessageConverter();
    }
    
    // 1. Definisikan Topic Exchange
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // 2. Definisikan Queue untuk Proyeksi (Konsumen)
    @Bean
    public Queue orderQueryQueue() {
        return new Queue(QUEUE_NAME_PROJECTION, false);
    }

    // 3. Ikat Queue ke Exchange dengan Routing Key yang sesuai
    @Bean
    public Binding bindingOrderCreated(Queue orderQueryQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(orderQueryQueue)
                             .to(orderExchange)
                             .with(ROUTING_KEY_ORDER_CREATED);
    }
}