package com.hltech.store.examples.aggregate;

import com.hltech.store.examples.aggregate.Events.OrderSent;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.hltech.store.examples.aggregate.Events.OrderCancelled;
import static com.hltech.store.examples.aggregate.Events.OrderPlaced;

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

    void sendOrder(UUID orderId) {
        Order order = repository.get(orderId);
        OrderSent event = order.send();
        repository.save(event);
    }

    Order getOrder(UUID orderId) {
        return repository.get(orderId);
    }

}
