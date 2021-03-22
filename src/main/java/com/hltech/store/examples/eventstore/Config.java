package com.hltech.store.examples.eventstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hltech.store.EventBodyMapper;
import com.hltech.store.EventStore;
import com.hltech.store.EventTypeMapper;
import com.hltech.store.JacksonEventBodyMapper;
import com.hltech.store.PostgresEventStore;
import com.hltech.store.SimpleEventTypeMapper;
import lombok.Getter;

import javax.sql.DataSource;

@Getter
class Config {

    private final EventTypeMapper<Event> eventTypeMapper;
    private final EventBodyMapper<Event> eventBodyMapper;
    private final EventStore<Event> eventStore;

    Config(DataSource dataSource) {
        eventTypeMapper = new SimpleEventTypeMapper<>();
        eventBodyMapper = new JacksonEventBodyMapper<>(new ObjectMapper());
        eventStore = new PostgresEventStore<>(
                Event::getId,
                Event::getAggregateId,
                eventTypeMapper,
                eventBodyMapper,
                dataSource
        );
    }

}
