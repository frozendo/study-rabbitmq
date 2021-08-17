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

    public static final String MULTI_VHOST_FANOUT_EXCHANGE = "multi-vhost-fanout-exchange";

    public static final String MULTI_VHOST_FANOUT_QUEUE = "multi-vhost-fanout-queue";
    public static final String OTHER_MULTI_VHOST_FANOUT_QUEUE = "other-multi-vhost-fanout-queue";

    @Autowired
    private ConnectionFactory connectionFactory;

    @PostConstruct
    public void createFanoutElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createFanoutExchange(rabbitAdmin);
        createMultiVhostFanoutQueue(rabbitAdmin);
        createOtherMultiVhostFanoutQueue(rabbitAdmin);
        createBindingMultiVhostQueueFanout(rabbitAdmin);
        createBindingOtherMultiVhostQueueFanout(rabbitAdmin);
    }

    private void createFanoutExchange(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .fanoutExchange(MULTI_VHOST_FANOUT_EXCHANGE)
                .build());
    }

    private void createBindingMultiVhostQueueFanout(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(MULTI_VHOST_FANOUT_QUEUE,
                Binding.DestinationType.QUEUE,
                MULTI_VHOST_FANOUT_EXCHANGE,
                "",
                null));
    }

    private void createBindingOtherMultiVhostQueueFanout(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(OTHER_MULTI_VHOST_FANOUT_QUEUE,
                Binding.DestinationType.QUEUE,
                MULTI_VHOST_FANOUT_EXCHANGE,
                "",
                null));
    }

    private void createMultiVhostFanoutQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(MULTI_VHOST_FANOUT_QUEUE)
                .build());
    }

    private void createOtherMultiVhostFanoutQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(OTHER_MULTI_VHOST_FANOUT_QUEUE)
                .build());
    }
}
