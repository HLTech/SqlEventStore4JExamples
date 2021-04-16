package com.hltech.store.examples.aggregate;

import com.hltech.store.EventStore;
import com.hltech.store.examples.eventstore.Event;
import com.hltech.store.versioning.MappingBasedVersioning;
import lombok.Getter;

@Getter
class Config {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    Config(
            EventStore<Event> eventStore,
            MappingBasedVersioning<Event> mappingBasedVersioning
    ) {
        configureEventTypeMapper(mappingBasedVersioning);
        orderRepository = new OrderRepository(eventStore);
        orderService = new OrderService(orderRepository);
    }

    void configureEventTypeMapper(MappingBasedVersioning<Event> mappingBasedVersioning) {
        mappingBasedVersioning.registerMapping(Events.OrderPlaced.class, "OrderPlaced");
        mappingBasedVersioning.registerMapping(Events.OrderCancelled.class, "OrderCancelled");
        mappingBasedVersioning.registerMapping(Events.OrderSent.class, "OrderSent");
    }

}
