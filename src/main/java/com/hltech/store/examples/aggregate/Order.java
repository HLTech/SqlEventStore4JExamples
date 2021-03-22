package com.hltech.store.examples.aggregate;

import com.hltech.store.examples.eventstore.Event;

import java.util.UUID;

import static com.hltech.store.examples.eventstore.Event.generateEventId;

class Order {

    UUID orderId;
    String status;

    Order apply(Event event) {
        if (Events.OrderPlaced.class.equals(event.getClass())) {
            status = "Placed";
            orderId = event.getAggregateId();
        } else if (Events.OrderCancelled.class.equals(event.getClass())) {
            status = "Cancelled";
        } else if (Events.OrderSent.class.equals(event.getClass())) {
            status = "Sent";
        }
        return this;
    }

    Events.OrderCancelled cancel(String reason) {
        if ("Sent".equals(status)) {
            throw new IllegalStateException("Once an order has been sent, it cannot be canceled");
        }
        return new Events.OrderCancelled(
                generateEventId(),
                orderId,
                reason
        );
    }

    Events.OrderSent send() {
        if ("Cancelled".equals(status)) {
            throw new IllegalStateException("Once an order has been cancelled, it cannot be sent");
        }
        return new Events.OrderSent(
                generateEventId(),
                orderId
        );
    }

}
