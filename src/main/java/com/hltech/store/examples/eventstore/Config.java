package com.hltech.store.examples.eventstore;

import com.hltech.store.EventStore;
import com.hltech.store.PostgresEventStore;
import com.hltech.store.versioning.EventVersioningStrategy;
import lombok.Getter;

import javax.sql.DataSource;

@Getter
class Config {

    private final EventStore<Event> eventStore;

    Config(
            DataSource dataSource,
            EventVersioningStrategy<Event> eventVersioningStrategy
    ) {
        eventStore = new PostgresEventStore<>(
                Event::getId,
                Event::getAggregateId,
                eventVersioningStrategy,
                dataSource
        );
    }

}
