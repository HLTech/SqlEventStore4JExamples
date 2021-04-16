package com.hltech.store.examples.aggregate.optimisticlocking;

import com.hltech.store.OptimisticLockingException;
import com.hltech.store.examples.aggregate.optimisticlocking.Events.OrderSent;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.hltech.store.examples.aggregate.optimisticlocking.Events.OrderCancelled;
import static com.hltech.store.examples.aggregate.optimisticlocking.Events.OrderPlaced;

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
        try {
            repository.save(event);
        } catch (OptimisticLockingException ex) {
            cancelOrder(orderId, reason);
        }
    }

    void sendOrder(UUID orderId) {
        Order order = repository.get(orderId);
        OrderSent event = order.send();
        try {
            repository.save(event);
        } catch (OptimisticLockingException ex) {
            sendOrder(orderId);
        }
    }

    Order getOrder(UUID orderId) {
        return repository.get(orderId);
    }

}
