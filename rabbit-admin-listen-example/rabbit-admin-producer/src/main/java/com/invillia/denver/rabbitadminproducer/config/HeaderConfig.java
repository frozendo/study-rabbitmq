package com.invillia.denver.rabbitadminproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
public class HeaderConfig {

    public static final String ADMIN_LISTEN_HEADER_EXCHANGE = "admin-listen-header-exchange";

    public static final String ADMIN_LISTEN_HEADER_QUEUE = "admin-listen-header-queue";
    public static final String OTHER_ADMIN_LISTEN_HEADER_QUEUE = "other-admin-listen-header-queue";

    public static final String TEST_HEADER_KEY = "test";
    public static final String TYPE_HEADER_KEY = "type";
    public static final String X_MATCH_HEADER_KEY = "x-match";

    @Autowired
    private ConnectionFactory connectionFactory;

    @PostConstruct
    private void createHeaderElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createHeaderExchange(rabbitAdmin);
        createAdminListenHeaderQueue(rabbitAdmin);
        createOtherAdminListenHeaderQueue(rabbitAdmin);
        createBindingAdminListenQueueHeader(rabbitAdmin);
        createBindingOtherAdminListenQueueHeader(rabbitAdmin);
    }

    private void createHeaderExchange(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .headersExchange(ADMIN_LISTEN_HEADER_EXCHANGE)
                .build());
    }

    private void createBindingAdminListenQueueHeader(RabbitAdmin rabbitAdmin) {
        Map<String, Object> map = Map.of(
                TEST_HEADER_KEY, "example-test",
                TYPE_HEADER_KEY, "all-header",
                X_MATCH_HEADER_KEY, "all"
        );
        rabbitAdmin.declareBinding(new Binding(ADMIN_LISTEN_HEADER_QUEUE,
                Binding.DestinationType.QUEUE,
                ADMIN_LISTEN_HEADER_EXCHANGE,
                "",
                map));
    }

    private void createBindingOtherAdminListenQueueHeader(RabbitAdmin rabbitAdmin) {
        Map<String, Object> map = Map.of(
                TEST_HEADER_KEY, "other-test",
                TYPE_HEADER_KEY, "any-header",
                X_MATCH_HEADER_KEY, "any"
        );
        rabbitAdmin.declareBinding(new Binding(OTHER_ADMIN_LISTEN_HEADER_QUEUE,
                Binding.DestinationType.QUEUE,
                ADMIN_LISTEN_HEADER_EXCHANGE,
                "",
                map));
    }

    private void createAdminListenHeaderQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(ADMIN_LISTEN_HEADER_QUEUE)
                .build());
    }

    private void createOtherAdminListenHeaderQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(OTHER_ADMIN_LISTEN_HEADER_QUEUE)
                .build());
    }
}
