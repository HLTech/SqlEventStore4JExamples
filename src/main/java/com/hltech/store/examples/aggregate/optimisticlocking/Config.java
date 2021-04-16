package com.hltech.store.examples.aggregate.optimisticlocking;

import com.hltech.store.EventStore;
import com.hltech.store.examples.eventstore.Event;
import lombok.Getter;

@Getter
class Config {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    Config(EventStore<Event> eventStore) {
        orderRepository = new OrderRepository(eventStore);
        orderService = new OrderService(orderRepository);
    }

}
