package com.hltech.store.examples.aggregate;

import com.hltech.store.EventStore;
import com.hltech.store.EventTypeMapper;
import com.hltech.store.examples.eventstore.Event;
import lombok.Getter;

@Getter
class Config {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    Config(
            EventStore<Event> eventStore,
            EventTypeMapper<Event> eventTypeMapper
    ) {
        configureEventTypeMapper(eventTypeMapper);
        orderRepository = new OrderRepository(eventStore);
        orderService = new OrderService(orderRepository);
    }

    void configureEventTypeMapper(EventTypeMapper<Event> eventTypeMapper) {
        eventTypeMapper.registerMapping(Events.OrderPlaced.class, "OrderPlaced", 1);
        eventTypeMapper.registerMapping(Events.OrderCancelled.class, "OrderCancelled", 1);
        eventTypeMapper.registerMapping(Events.OrderSent.class, "OrderSent", 1);
    }

}
