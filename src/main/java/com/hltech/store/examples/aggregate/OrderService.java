package com.hltech.store.examples.aggregate;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.hltech.store.examples.common.Event.generateAggregateId;
import static com.hltech.store.examples.common.Event.generateEventId;

@RequiredArgsConstructor
class OrderService {

    private static final Runnable throwOrderNotFoundException = () -> {
        throw new IllegalStateException("Order not found");
    };

    private final OrderRepository repository;

    UUID placeOrder(String orderNumber) {
        Events.OrderPlaced event = new Events.OrderPlaced(
                generateEventId(),
                generateAggregateId(),
                orderNumber
        );
        repository.save(event);
        return event.getAggregateId();
    }

    Order getOrder(UUID orderId) {
        return repository.get(orderId);
    }

    void cancelOrder(UUID orderId, String reason) {
        repository
                .find(orderId)
                .map(order -> order.cancel(reason))
                .ifPresentOrElse(repository::save, throwOrderNotFoundException);
    }

    void sendOrder(UUID orderId) {
        repository
                .find(orderId)
                .map(Order::send)
                .ifPresentOrElse(repository::save, throwOrderNotFoundException);
    }

}
