package com.hltech.store.examples.event.versioning.multipleversions;

import com.hltech.store.examples.event.Event;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

import static com.hltech.store.examples.event.versioning.multipleversions.Events.OrderPlacedV1;
import static com.hltech.store.examples.event.versioning.multipleversions.Events.OrderPlacedV2;
import static com.hltech.store.examples.event.versioning.multipleversions.Events.OrderPlacedV3;

@Getter
class Order {

    private static final String DEFAULT_PRIORITY = "low";

    private UUID id;
    private String number;
    private String status;
    private Instant creationDate;
    private String priority;

    Order applyEvent(Event event) {
        if (event instanceof OrderPlacedV1) {
            applyOrderPlacedV1((OrderPlacedV1) event);
        } else if (event instanceof OrderPlacedV2) {
            applyOrderPlacedV2((OrderPlacedV2) event);
        }  else if (event instanceof OrderPlacedV3) {
            applyOrderPlacedV3((OrderPlacedV3) event);
        }
        return this;
    }

    private void applyOrderPlacedV1(OrderPlacedV1 event) {
        status = "Placed";
        id = event.getAggregateId();
        number = event.getOrderNumber();
        priority = DEFAULT_PRIORITY;
    }

    private void applyOrderPlacedV2(OrderPlacedV2 event) {
        status = "Placed";
        id = event.getAggregateId();
        number = event.getOrderNumber();
        creationDate = event.getCreationDate();
        priority = DEFAULT_PRIORITY;
    }

    private void applyOrderPlacedV3(OrderPlacedV3 event) {
        status = "Placed";
        id = event.getAggregateId();
        number = event.getOrderNumber();
        creationDate = event.getCreationDate();
        priority = event.getPriority();

    }

}
