package com.hltech.store.examples.event.versioning.multipleversions;

import com.hltech.store.AggregateRepository;
import com.hltech.store.EventStore;
import com.hltech.store.examples.event.Event;
import com.hltech.store.versioning.MultipleVersionsBasedVersioning;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import static com.hltech.store.examples.event.versioning.multipleversions.Events.OrderPlacedV1;
import static com.hltech.store.examples.event.versioning.multipleversions.Events.OrderPlacedV2;
import static com.hltech.store.examples.event.versioning.multipleversions.Events.OrderPlacedV3;

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
        registerEvents((MultipleVersionsBasedVersioning<Event>) eventStore.getEventVersioningStrategy());
    }

    private void registerEvents(MultipleVersionsBasedVersioning<Event> eventVersioningStrategy) {
        eventVersioningStrategy.registerEvent(OrderPlacedV1.class, "OrderPlaced", 1);
        eventVersioningStrategy.registerEvent(OrderPlacedV2.class, "OrderPlaced", 2);
        eventVersioningStrategy.registerEvent(OrderPlacedV3.class, "OrderPlaced", 3);
    }

}
