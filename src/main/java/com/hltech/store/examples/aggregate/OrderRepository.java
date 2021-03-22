package com.hltech.store.examples.aggregate;

import com.hltech.store.AggregateRepository;
import com.hltech.store.EventStore;
import com.hltech.store.examples.eventstore.Event;

import java.util.function.BiFunction;
import java.util.function.Supplier;

class OrderRepository extends AggregateRepository<Order, Event> {

    private static final Supplier<Order> INITIAL_AGGREGATE_STATE_SUPPLIER = Order::new;
    private static final BiFunction<Order, Event, Order> AGGREGATE_EVENT_APPLIER = Order::apply;
    private static final String STREAM_NAME = "OrderStream";

    public OrderRepository(EventStore<Event> eventStore) {
        super(
                eventStore,
                INITIAL_AGGREGATE_STATE_SUPPLIER,
                AGGREGATE_EVENT_APPLIER,
                STREAM_NAME
        );
    }

}
