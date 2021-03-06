package com.hltech.store.examples.aggregate.basic;

import com.hltech.store.examples.event.Event;
import lombok.Getter;

import java.util.UUID;

import static com.hltech.store.examples.aggregate.basic.Events.OrderCancelled;
import static com.hltech.store.examples.aggregate.basic.Events.OrderPlaced;
import static com.hltech.store.examples.event.Event.generateAggregateId;
import static com.hltech.store.examples.event.Event.generateEventId;

@Getter
class Order {

    private UUID id;
    private String number;
    private String status;
    private String cancellationReason;

    static OrderPlaced place(String orderNumber) {
        return new OrderPlaced(
                generateEventId(),
                generateAggregateId(),
                orderNumber
        );
    }

    OrderCancelled cancel(String reason) {
        return new OrderCancelled(generateEventId(), id, reason);
    }

    Order applyEvent(Event event) {
        if (event instanceof OrderPlaced) {
            applyOrderPlaced((OrderPlaced) event);
        } else if (event instanceof OrderCancelled) {
            applyOrderCancelled((OrderCancelled) event);
        }
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

}
