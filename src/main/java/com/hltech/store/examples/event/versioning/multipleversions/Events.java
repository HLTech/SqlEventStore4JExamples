package com.hltech.store.examples.event.versioning.multipleversions;

import com.hltech.store.examples.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

interface Events {

    @RequiredArgsConstructor
    @Getter
    class OrderPlacedV1 implements Event {

        private final UUID id;
        private final UUID aggregateId;
        private final String orderNumber;

    }

    @RequiredArgsConstructor
    @Getter
    class OrderPlacedV2 implements Event {

        private final UUID id;
        private final UUID aggregateId;
        private final String orderNumber;
        private final Instant creationDate;

    }

    @RequiredArgsConstructor
    @Getter
    class OrderPlacedV3 implements Event {

        private final UUID id;
        private final UUID aggregateId;
        private final String orderNumber;
        private final Instant creationDate;
        private final String priority;

    }

}
