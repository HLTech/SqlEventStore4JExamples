package com.hltech.store.examples.eventstore

import com.hltech.store.EventBodyMapper
import com.hltech.store.EventStore
import com.hltech.store.EventTypeMapper
import com.hltech.store.PostgreSQLContainerSpecification

class EventStoreSpecification extends PostgreSQLContainerSpecification {

    private Config config = new Config(dataSource)
    EventTypeMapper<Event> eventTypeMapper = config.eventTypeMapper
    EventBodyMapper<Event> eventBodyMapper = config.eventBodyMapper
    EventStore<Event> eventStore = config.eventStore

}
