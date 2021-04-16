package com.hltech.store.examples.aggregate.basic;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.hltech.store.examples.aggregate.basic.Events.OrderCancelled;
import static com.hltech.store.examples.aggregate.basic.Events.OrderPlaced;

@RequiredArgsConstructor
class OrderService {

    private final OrderRepository repository;

    UUID placeOrder(String orderNumber) {
        OrderPlaced event = Order.place(orderNumber);
        repository.save(event);
        return event.getAggregateId();
    }

    void cancelOrder(UUID orderId, String reason) {
        Order order = repository.get(orderId);
        OrderCancelled event = order.cancel(reason);
        repository.save(event);
    }

    Order getOrder(UUID orderId) {
        return repository.get(orderId);
    }

}
