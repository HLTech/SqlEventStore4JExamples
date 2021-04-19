package com.hltech.store.examples.aggregate.basic;

import com.hltech.store.examples.event.Event;
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

}
