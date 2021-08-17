package com.invillia.denver.sampleproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DirectConfig {

    public static final String SAMPLE_MANUAL_DIRECT_EXCHANGE = "sample-manual-direct-exchange";

    public static final String SAMPLE_MANUAL_DIRECT_QUEUE = "sample-manual-direct-queue";
    public static final String OTHER_SAMPLE_MANUAL_DIRECT_QUEUE = "other-sample-manual-direct-queue";

    public static final String EXAMPLE_QUEUE_BINDING = "example.queue.binding";
    public static final String OTHER_QUEUE_BINDING = "other.queue.binding";

    @Autowired
    private ConnectionFactory connectionFactory;

    @PostConstruct
    public void createDirectElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createDirectExchange(rabbitAdmin);
        createSampleManualQueue(rabbitAdmin);
        createOtherSampleManualQueue(rabbitAdmin);
        createBindingSampleManualQueue(rabbitAdmin);
        createBindingOtherSampleManuelQueue(rabbitAdmin);
    }

    private void createDirectExchange(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .directExchange(SAMPLE_MANUAL_DIRECT_EXCHANGE)
                .build());
    }

    private void createBindingSampleManualQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(SAMPLE_MANUAL_DIRECT_QUEUE,
                Binding.DestinationType.QUEUE,
                SAMPLE_MANUAL_DIRECT_EXCHANGE,
                EXAMPLE_QUEUE_BINDING,
                null));
    }

    private void createBindingOtherSampleManuelQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(OTHER_SAMPLE_MANUAL_DIRECT_QUEUE,
                Binding.DestinationType.QUEUE,
                SAMPLE_MANUAL_DIRECT_EXCHANGE,
                OTHER_QUEUE_BINDING,
                null));
    }

    private void createSampleManualQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(SAMPLE_MANUAL_DIRECT_QUEUE)
                .build());
    }

    private void createOtherSampleManualQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(OTHER_SAMPLE_MANUAL_DIRECT_QUEUE)
                .build());
    }

}
