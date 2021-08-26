package com.frozendo.rabbit.sample.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static com.frozendo.rabbit.sample.domain.enums.FanoutEnum.INVENTORY_QUEUE;
import static com.frozendo.rabbit.sample.domain.enums.FanoutEnum.SPRING_FANOUT_PRODUCT_EX;
import static com.frozendo.rabbit.sample.domain.enums.FanoutEnum.PAYMENT_QUEUE;

@Configuration
public class FanoutExchangeConfig {

    private final ConnectionFactory connectionFactory;

    public FanoutExchangeConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @PostConstruct
    public void createFanoutElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createExchange(rabbitAdmin);
        createQueues(rabbitAdmin);
        doBinding(rabbitAdmin);
    }

    private void createExchange(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .fanoutExchange(SPRING_FANOUT_PRODUCT_EX.getValue())
                .build());
    }

    private void createQueues(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(INVENTORY_QUEUE.getValue())
                .build());
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(PAYMENT_QUEUE.getValue())
                .build());
    }

    private void doBinding(RabbitAdmin rabbitAdmin) {
        var inventoryBinding = new Binding(INVENTORY_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                SPRING_FANOUT_PRODUCT_EX.getValue(),
                StringUtils.EMPTY, null);
        var paymentBinding = new Binding(PAYMENT_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                SPRING_FANOUT_PRODUCT_EX.getValue(),
                StringUtils.EMPTY, null);

        rabbitAdmin.declareBinding(inventoryBinding);
        rabbitAdmin.declareBinding(paymentBinding);
    }
}
