package com.hltech.store.examples.aggregate.optimisticlocking;

import com.hltech.store.examples.eventstore.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

interface Events {

    @RequiredArgsConstructor
    @Getter
    class OrderPlaced implements Event {

        private final UUID id;
        private final UUID aggregateId;
        private final String orderNumber;

    }

    @RequiredArgsConstructor
    @Getter
    class OrderCancelled implements Event {

        private final UUID id;
        private final UUID aggregateId;
        private final String reason;

    }

    @RequiredArgsConstructor
    @Getter
    class OrderSent implements Event {

        private final UUID id;
        private final UUID aggregateId;

    }

}
