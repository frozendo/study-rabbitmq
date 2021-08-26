package com.frozendo.rabbit.sample.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.BIG_QUANTITY_KEY;
import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.BIG_QUANTITY_QUEUE;
import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.SPRING_DIRECT_PRODUCT_EX;
import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.SMALL_QUANTITY_KEY;
import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.SMALL_QUANTITY_QUEUE;

@Configuration
public class DirectExchangeConfig {

    private final ConnectionFactory connectionFactory;

    public DirectExchangeConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @PostConstruct
    public void createDirectElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createExchange(rabbitAdmin);
        createQueues(rabbitAdmin);
        doBinding(rabbitAdmin);
    }

    private void createExchange(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .directExchange(SPRING_DIRECT_PRODUCT_EX.getValue())
                .build());
    }

    private void createQueues(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(BIG_QUANTITY_QUEUE.getValue())
                .build());

        rabbitAdmin.declareQueue(QueueBuilder
                .durable(SMALL_QUANTITY_QUEUE.getValue())
                .build());
    }

    private void doBinding(RabbitAdmin rabbitAdmin) {
        var bigQuantityBinding = new Binding(BIG_QUANTITY_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                SPRING_DIRECT_PRODUCT_EX.getValue(),
                BIG_QUANTITY_KEY.getValue(),
                null);

        var smallQuantityBinding = new Binding(SMALL_QUANTITY_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                SPRING_DIRECT_PRODUCT_EX.getValue(),
                SMALL_QUANTITY_KEY.getValue(),
                null);

        rabbitAdmin.declareBinding(bigQuantityBinding);
        rabbitAdmin.declareBinding(smallQuantityBinding);
    }

}
