package com.hltech.store.examples.event.versioning.mapping;

import com.hltech.store.examples.event.Event;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

import static com.hltech.store.examples.event.versioning.mapping.Events.OrderPlaced;

@Getter
class Order {

    private UUID id;
    private String number;
    private String status;
    private Instant creationDate;
    private String priority;

    Order applyEvent(Event event) {
        if (event instanceof OrderPlaced) {
            applyOrderPlaced((OrderPlaced) event);
        }
        return this;
    }

    private void applyOrderPlaced(OrderPlaced event) {
        status = "Placed";
        id = event.getAggregateId();
        number = event.getOrderNumber();
        creationDate = event.getCreationDate();
        priority = event.getPriority();
    }

}
