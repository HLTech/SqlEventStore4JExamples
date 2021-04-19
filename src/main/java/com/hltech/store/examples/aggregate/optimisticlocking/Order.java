package com.hltech.store.examples.aggregate.optimisticlocking;

import com.hltech.store.examples.event.Event;
import lombok.Getter;

import java.util.UUID;

import static com.hltech.store.examples.aggregate.optimisticlocking.Events.OrderCancelled;
import static com.hltech.store.examples.aggregate.optimisticlocking.Events.OrderPlaced;
import static com.hltech.store.examples.aggregate.optimisticlocking.Events.OrderSent;
import static com.hltech.store.examples.event.Event.generateAggregateId;
import static com.hltech.store.examples.event.Event.generateEventId;

@Getter
class Order {

    private UUID id;
    private String number;
    private String status;
    private String cancellationReason;
    private Integer version;

    static OrderPlaced place(String orderNumber) {
        return new OrderPlaced(
                generateEventId(),
                generateAggregateId(),
                orderNumber
        );
    }

    OrderCancelled cancel(String reason) {
        if ("Sent".equals(status)) {
            throw new IllegalStateException("Once an order has been sent, it cannot be canceled");
        }
        return new OrderCancelled(generateEventId(), id, reason);
    }

    OrderSent send() {
        if ("Cancelled".equals(status)) {
            throw new IllegalStateException("Once an order has been cancelled, it cannot be sent");
        }
        return new OrderSent(generateEventId(), id);
    }

    Order applyEvent(Event event) {
        if (event instanceof OrderPlaced) {
            applyOrderPlaced((OrderPlaced) event);
        } else if (event instanceof OrderCancelled) {
            applyOrderCancelled((OrderCancelled) event);
        } else if (event instanceof OrderSent) {
            applyOrderSent();
        }
        return this;
    }

    Order applyVersion(Integer version) {
        this.version = version;
        return this;
    }

    private void applyOrderPlaced(OrderPlaced event) {
        status = "Placed";
        id = event.getAggregateId();
        number = event.getOrderNumber();
    }

    private void applyOrderCancelled(OrderCancelled event) {
        status = "Cancelled";
        cancellationReason = event.getReason();
    }

    private void applyOrderSent() {
        status = "Sent";
    }

}
