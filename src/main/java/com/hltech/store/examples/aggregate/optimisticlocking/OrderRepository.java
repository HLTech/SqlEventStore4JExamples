package com.hltech.store.examples.aggregate.optimisticlocking;

import com.hltech.store.AggregateRepository;
import com.hltech.store.EventStore;
import com.hltech.store.examples.event.Event;
import com.hltech.store.versioning.MappingBasedVersioning;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import static com.hltech.store.examples.aggregate.optimisticlocking.Events.OrderCancelled;
import static com.hltech.store.examples.aggregate.optimisticlocking.Events.OrderPlaced;
import static com.hltech.store.examples.aggregate.optimisticlocking.Events.OrderSent;

class OrderRepository extends AggregateRepository<Order, Event> {

    private static final Supplier<Order> INITIAL_AGGREGATE_STATE_SUPPLIER = Order::new;
    private static final BiFunction<Order, Event, Order> AGGREGATE_EVENT_APPLIER = Order::applyEvent;
    private static final BiFunction<Order, Integer, Order> AGGREGATE_VERSION_APPLIER = Order::applyVersion;
    private static final String AGGREGATE_NAME = "Order";

    public OrderRepository(EventStore<Event> eventStore) {
        super(
                eventStore,
                AGGREGATE_NAME,
                INITIAL_AGGREGATE_STATE_SUPPLIER,
                AGGREGATE_EVENT_APPLIER,
                AGGREGATE_VERSION_APPLIER
        );
        registerEvents((MappingBasedVersioning<Event>) eventStore.getEventVersioningStrategy());
    }

    private void registerEvents(MappingBasedVersioning<Event> eventVersioningStrategy) {
        eventVersioningStrategy.registerEvent(OrderPlaced.class, "OrderPlaced");
        eventVersioningStrategy.registerEvent(OrderCancelled.class, "OrderCancelled");
        eventVersioningStrategy.registerEvent(OrderSent.class, "OrderSent");
    }

}
