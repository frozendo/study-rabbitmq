package com.invillia.denver.rabbitadminproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Configuration
public class DirectConfig {

    public static final String ADMIN_LISTEN_DIRECT_EXCHANGE = "admin-listen-direct-exchange";

    public static final String ADMIN_LISTEN_DIRECT_QUEUE = "admin-listen-direct-queue";
    public static final String OTHER_ADMIN_LISTEN_DIRECT_QUEUE = "other-admin-listen-direct-queue";

    public static final String ADMIN_QUEUE_BINDING = "admin.queue.binding";
    public static final String OTHER_QUEUE_BINDING = "other.queue.binding";

    @Autowired
    private ConnectionFactory connectionFactory;

    @PostConstruct
    public void createDirectElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createDirectExchange(rabbitAdmin);
        createAdminListenDirectQueue(rabbitAdmin);
        createOtherAdminListenDirectQueue(rabbitAdmin);
        createBindingAdminListenQueueDirect(rabbitAdmin);
        createBindingOtherAdminListenQueueDirect(rabbitAdmin);
    }

    private void createDirectExchange(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .directExchange(ADMIN_LISTEN_DIRECT_EXCHANGE)
                .build());
    }

    private void createBindingAdminListenQueueDirect(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(ADMIN_LISTEN_DIRECT_QUEUE,
                Binding.DestinationType.QUEUE,
                ADMIN_LISTEN_DIRECT_EXCHANGE,
                ADMIN_QUEUE_BINDING,
                null));
    }

    private void createBindingOtherAdminListenQueueDirect(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(OTHER_ADMIN_LISTEN_DIRECT_QUEUE,
                Binding.DestinationType.QUEUE,
                ADMIN_LISTEN_DIRECT_EXCHANGE,
                OTHER_QUEUE_BINDING,
                null));
    }

    private void createAdminListenDirectQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(ADMIN_LISTEN_DIRECT_QUEUE)
                .build());
    }

    private void createOtherAdminListenDirectQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(OTHER_ADMIN_LISTEN_DIRECT_QUEUE)
                .build());
    }

}
