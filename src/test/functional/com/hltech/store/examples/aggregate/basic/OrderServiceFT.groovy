package com.hltech.store.examples.aggregate.basic

import com.hltech.store.EventStore
import com.hltech.store.examples.PostgreSQLContainerSpecification
import com.hltech.store.PostgresEventStore
import com.hltech.store.examples.event.Event
import com.hltech.store.versioning.EventVersioningStrategy
import com.hltech.store.versioning.MappingBasedVersioning
import spock.lang.Subject

import static org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

class OrderServiceFT extends PostgreSQLContainerSpecification {

    EventVersioningStrategy<Event> eventVersioningStrategy = new MappingBasedVersioning()
    EventStore<Event> eventStore = new PostgresEventStore<>(
            { Event event -> event.getId() },
            { Event event -> event.getAggregateId() },
            eventVersioningStrategy,
            dataSource
    )
    OrderRepository orderRepository = new OrderRepository(eventStore)

    @Subject
    OrderService service = new OrderService(orderRepository)

    def "placeOrder should place an order"() {

        when: 'place an order'
            def orderId = service.placeOrder(ORDER_NUMBER)

        then: 'order exist'
            def order = service.getOrder(orderId)

        and: 'order placed'
            with (order) {
                assert status == 'Placed'
                assert number == ORDER_NUMBER
            }

    }

    def "cancelOrder should cancel order"() {

        given: 'order placed'
            def orderId = service.placeOrder(ORDER_NUMBER)

        when: 'cancel order'
            service.cancelOrder(orderId, ORDER_CANCELLATION_REASON)

        then: 'order exist'
            def order = service.getOrder(orderId)

        then: 'order cancelled'
            with (order) {
                assert status == 'Cancelled'
                assert cancellationReason == ORDER_CANCELLATION_REASON
            }

    }

    static ORDER_NUMBER = randomAlphanumeric(5)
    static ORDER_CANCELLATION_REASON = randomAlphanumeric(5)

}
