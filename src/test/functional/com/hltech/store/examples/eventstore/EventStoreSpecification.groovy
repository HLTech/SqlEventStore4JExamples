package com.hltech.store.examples.eventstore

import com.hltech.store.EventStore
import com.hltech.store.PostgreSQLContainerSpecification
import com.hltech.store.versioning.EventVersioningStrategy
import com.hltech.store.versioning.MappingBasedVersioning

class EventStoreSpecification extends PostgreSQLContainerSpecification {

    EventVersioningStrategy<Event> eventVersioningStrategy = new MappingBasedVersioning()
    private Config config = new Config(dataSource, eventVersioningStrategy)
    EventStore<Event> eventStore = config.eventStore

}
