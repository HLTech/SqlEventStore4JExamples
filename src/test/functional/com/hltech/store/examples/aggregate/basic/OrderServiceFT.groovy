package com.hltech.store.examples.aggregate.basic

import com.hltech.store.examples.eventstore.EventStoreSpecification
import spock.lang.Subject

import static org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

class OrderServiceFT extends EventStoreSpecification {

    Config config = new Config(eventStore, eventVersioningStrategy)

    @Subject
    def service = config.orderService

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
