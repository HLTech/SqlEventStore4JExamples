package com.hltech.store.examples.aggregate

import com.fasterxml.jackson.databind.ObjectMapper
import com.hltech.store.EventBodyMapper
import com.hltech.store.EventStore
import com.hltech.store.EventTypeMapper
import com.hltech.store.JacksonEventBodyMapper
import com.hltech.store.PostgreSQLContainerTest
import com.hltech.store.PostgresEventStore
import com.hltech.store.SimpleEventTypeMapper
import com.hltech.store.examples.common.Event
import spock.lang.Subject

import java.util.function.Function

import static com.hltech.store.EventTypeMapper.TypeNameAndVersion
import static com.hltech.store.examples.aggregate.Events.OrderCancelled
import static com.hltech.store.examples.aggregate.Events.OrderPlaced
import static com.hltech.store.examples.aggregate.Events.OrderSent
import static org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

class OrderServiceFT extends PostgreSQLContainerTest {

    @Subject
    def service

    def "placeOrder should place an order"() {

        when: 'place an order'
            def orderId = service.placeOrder(ORDER_NUMBER)

        then: 'order placed'
            service.getOrder(orderId).status == 'Placed'

    }

    def "cancelOrder should cancel order"() {

        given: 'order placed'
            def orderId = service.placeOrder(ORDER_NUMBER)

        when: 'cancel order'
            service.cancelOrder(orderId, ORDER_CANCELLATION_REASON)

        then: 'order cancelled'
            service.getOrder(orderId).status == 'Cancelled'

    }

    def "cancelOrder should throw exception when order sent"() {

        given: 'order placed'
            def orderId = service.placeOrder(ORDER_NUMBER)

        and: 'order sent'
            service.sendOrder(orderId)

        when: 'cancel order'
            service.cancelOrder(orderId, ORDER_CANCELLATION_REASON)

        then: 'exception thrown'
            def ex = thrown(IllegalStateException)
            ex.message == 'Once an order has been sent, it cannot be canceled'

    }

    def "sendOrder should send order"() {

        given: 'order placed'
            def orderId = service.placeOrder(ORDER_NUMBER)

        when: 'cancel order'
            service.sendOrder(orderId)

        then: 'order placed'
            service.getOrder(orderId).status == 'Sent'

    }

    def "sendOrder should throw exception when order cancelled"() {

        given: 'order placed'
            def orderId = service.placeOrder(ORDER_NUMBER)

        and: 'order cancelled'
            service.cancelOrder(orderId, ORDER_CANCELLATION_REASON)

        when: 'send order'
            service.sendOrder(orderId)

        then: 'exception thrown'
            def ex = thrown(IllegalStateException)
            ex.message == 'Once an order has been cancelled, it cannot be sent'

    }

    static ORDER_NUMBER = randomAlphanumeric(5)
    static ORDER_CANCELLATION_REASON = randomAlphanumeric(5)

    def setup() {
        Function<Event, UUID> eventIdExtractor = { it.id }
        Function<Event, UUID> aggregateIdExtractor = { it.aggregateId }
        EventTypeMapper<Event> eventTypeMapper = new SimpleEventTypeMapper<>(
                [
                        new TypeNameAndVersion(OrderPlaced, "OrderPlaced", 1),
                        new TypeNameAndVersion(OrderCancelled, "OrderCancelled", 1),
                        new TypeNameAndVersion(OrderSent, "OrderSent", 1)
                ]
        )
        EventBodyMapper<Event> eventBodyMapper = new JacksonEventBodyMapper<>(new ObjectMapper())
        EventStore<Event> eventStore = new PostgresEventStore(
                eventIdExtractor,
                aggregateIdExtractor,
                eventTypeMapper,
                eventBodyMapper,
                PostgreSQLContainerTest.dataSource
        )
        def repository = new OrderRepository(eventStore)
        service = new OrderService(repository)
    }

}
