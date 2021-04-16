package com.hltech.store.examples.aggregate.basic;

import com.hltech.store.examples.eventstore.Event;
import lombok.Getter;

import java.util.UUID;

import static com.hltech.store.examples.aggregate.basic.Events.OrderCancelled;
import static com.hltech.store.examples.aggregate.basic.Events.OrderPlaced;
import static com.hltech.store.examples.eventstore.Event.generateAggregateId;
import static com.hltech.store.examples.eventstore.Event.generateEventId;

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
        if (OrderPlaced.class.equals(event.getClass())) {
            status = "Placed";
            id = event.getAggregateId();
            number = ((OrderPlaced) event).getOrderNumber();
        } else if (OrderCancelled.class.equals(event.getClass())) {
            status = "Cancelled";
            cancellationReason = ((OrderCancelled) event).getReason();
        }
        return this;
    }

}
