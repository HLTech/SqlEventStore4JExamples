package com.hltech.store.examples.aggregate.basic;

import com.hltech.store.AggregateRepository;
import com.hltech.store.EventStore;
import com.hltech.store.examples.eventstore.Event;
import com.hltech.store.versioning.MappingBasedVersioning;

import java.util.function.BiFunction;
import java.util.function.Supplier;

class OrderRepository extends AggregateRepository<Order, Event> {

    private static final Supplier<Order> INITIAL_AGGREGATE_STATE_SUPPLIER = Order::new;
    private static final BiFunction<Order, Event, Order> AGGREGATE_EVENT_APPLIER = Order::applyEvent;
    private static final String AGGREGATE_NAME = "Order";

    public OrderRepository(EventStore<Event> eventStore) {
        super(
                eventStore,
                AGGREGATE_NAME,
                INITIAL_AGGREGATE_STATE_SUPPLIER,
                AGGREGATE_EVENT_APPLIER
        );
        registerEvents((MappingBasedVersioning<Event>) eventStore.getEventVersioningStrategy());
    }

    private void registerEvents(MappingBasedVersioning<Event> eventVersioningStrategy) {
        eventVersioningStrategy.registerMapping(Events.OrderPlaced.class, "OrderPlaced");
        eventVersioningStrategy.registerMapping(Events.OrderCancelled.class, "OrderCancelled");
    }

}
