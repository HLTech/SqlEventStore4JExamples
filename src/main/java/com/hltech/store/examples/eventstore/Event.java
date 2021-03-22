package com.hltech.store.examples.eventstore;

import java.util.UUID;

public interface Event {

    UUID getId();

    UUID getAggregateId();

    static UUID generateEventId() {
        return UUID.randomUUID();
    }

    static UUID generateAggregateId() {
        return UUID.randomUUID();
    }

}
