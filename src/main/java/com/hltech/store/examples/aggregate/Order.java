package com.hltech.store.examples.aggregate;

import com.hltech.store.examples.eventstore.Event;
import lombok.Getter;

import java.util.UUID;

import static com.hltech.store.examples.aggregate.Events.OrderCancelled;
import static com.hltech.store.examples.aggregate.Events.OrderPlaced;
import static com.hltech.store.examples.aggregate.Events.OrderSent;
import static com.hltech.store.examples.eventstore.Event.generateAggregateId;
import static com.hltech.store.examples.eventstore.Event.generateEventId;

@Getter
class Order {

    private UUID id;
    private String number;
    private String status;
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
        if (OrderPlaced.class.equals(event.getClass())) {
            status = "Placed";
            id = event.getAggregateId();
            number = ((OrderPlaced) event).getOrderNumber();
        } else if (OrderCancelled.class.equals(event.getClass())) {
            status = "Cancelled";
        } else if (OrderSent.class.equals(event.getClass())) {
            status = "Sent";
        }
        return this;
    }

    Order applyVersion(Integer version) {
        this.version = version;
        return this;
    }

}
