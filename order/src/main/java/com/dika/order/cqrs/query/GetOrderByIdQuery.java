package com.dika.order.cqrs.query;

public class GetOrderByIdQuery {
    private Long id;

    public GetOrderByIdQuery(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
