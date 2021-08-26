package com.frozendo.rabbit.multi.config;

import com.frozendo.rabbit.multi.consumer.fanout.InventoryConsumerService;
import com.frozendo.rabbit.multi.consumer.fanout.PaymentConsumerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static com.frozendo.rabbit.multi.domain.enums.FanoutEnum.INVENTORY_QUEUE;
import static com.frozendo.rabbit.multi.domain.enums.FanoutEnum.PAYMENT_QUEUE;
import static com.frozendo.rabbit.multi.domain.enums.FanoutEnum.SPRING_FANOUT_PRODUCT_EX;

@Configuration
public class FanoutExchangeConfig {

    private final ConnectionFactory connectionFactory;
    private final InventoryConsumerService inventoryConsumerService;
    private final PaymentConsumerService paymentConsumerService;

    public FanoutExchangeConfig(@Qualifier("fanoutConnectionFactory") ConnectionFactory connectionFactory,
                                InventoryConsumerService inventoryConsumerService,
                                PaymentConsumerService paymentConsumerService) {
        this.connectionFactory = connectionFactory;
        this.inventoryConsumerService = inventoryConsumerService;
        this.paymentConsumerService = paymentConsumerService;
    }

    @PostConstruct
    public void createFanoutElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createExchange(rabbitAdmin);
        createQueues(rabbitAdmin);
        doBinding(rabbitAdmin);
    }

    @Bean
    public MessageListenerContainer inventoryListener() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(INVENTORY_QUEUE.getValue());
        container.setMessageListener(inventoryConsumerService);
        container.start();
        return container;
    }

    @Bean
    public MessageListenerContainer paymentListener() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(PAYMENT_QUEUE.getValue());
        container.setMessageListener(paymentConsumerService);
        container.start();
        return container;
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
