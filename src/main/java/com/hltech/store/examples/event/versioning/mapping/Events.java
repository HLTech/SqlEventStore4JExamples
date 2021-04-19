package com.hltech.store.examples.event.versioning.mapping;

import com.hltech.store.examples.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

interface Events {

    @RequiredArgsConstructor
    @Getter
    class OrderPlaced implements Event {

        private static final String DEFAULT_PRIORITY = "low";

        private final UUID id;
        private final UUID aggregateId;
        private final String orderNumber;
        private final Instant creationDate;
        private final String priority;

        String getPriority() {
            return priority != null ? priority : DEFAULT_PRIORITY;
        }

    }

}
