package com.dika.order.cqrs.event;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventStore {

    private final List<Object> events = new ArrayList<>();

    public void save(Object event) {
        events.add(event);
        System.out.println("💾 Event disimpan ke EventStore: " + event);
    }

    public List<Object> getAllEvents() {
        return events;
    }
}
