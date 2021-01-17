package com.invillia.denver.multivhostproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FanoutConfig {

    public static final String ADMIN_LISTEN_FANOUT_EXCHANGE = "multi-vhost-fanout-exchange";

    public static final String ADMIN_LISTEN_FANOUT_QUEUE = "multi-vhost-fanout-queue";
    public static final String OTHER_ADMIN_LISTEN_FANOUT_QUEUE = "other-multi-vhost-fanout-queue";

    @Autowired
    private ConnectionFactory connectionFactory;

    @PostConstruct
    public void createFanoutElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createFanoutExchange(rabbitAdmin);
        createAdminListenFanoutQueue(rabbitAdmin);
        createOtherAdminListenFanoutQueue(rabbitAdmin);
        createBindingAdminListenQueueFanout(rabbitAdmin);
        createBindingOtherAdminListenQueueFanout(rabbitAdmin);
    }

    private void createFanoutExchange(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .fanoutExchange(ADMIN_LISTEN_FANOUT_EXCHANGE)
                .build());
    }

    private void createBindingAdminListenQueueFanout(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(ADMIN_LISTEN_FANOUT_QUEUE,
                Binding.DestinationType.QUEUE,
                ADMIN_LISTEN_FANOUT_EXCHANGE,
                "",
                null));
    }

    private void createBindingOtherAdminListenQueueFanout(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(OTHER_ADMIN_LISTEN_FANOUT_QUEUE,
                Binding.DestinationType.QUEUE,
                ADMIN_LISTEN_FANOUT_EXCHANGE,
                "",
                null));
    }

    private void createAdminListenFanoutQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(ADMIN_LISTEN_FANOUT_QUEUE)
                .build());
    }

    private void createOtherAdminListenFanoutQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(OTHER_ADMIN_LISTEN_FANOUT_QUEUE)
                .build());
    }
}
