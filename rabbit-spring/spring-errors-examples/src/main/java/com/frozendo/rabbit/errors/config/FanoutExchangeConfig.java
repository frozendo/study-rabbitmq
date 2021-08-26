package com.frozendo.rabbit.errors.config;

import com.frozendo.rabbit.errors.domain.enums.DlqEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.INVENTORY_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.INVENTORY_DLQ_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.INVENTORY_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.INVENTORY_DLQ_KEY;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.PAYMENT_DLQ_KEY;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.SPRING_FANOUT_PRODUCT_EX;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.PAYMENT_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.PAYMENT_DLQ_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.PAYMENT_QUEUE;

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
        createDelayedQueues(rabbitAdmin);
        createDlqQueues(rabbitAdmin);
        doBinding(rabbitAdmin);
        doDlqBinding(rabbitAdmin);
    }

    private void createExchange(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .fanoutExchange(SPRING_FANOUT_PRODUCT_EX.getValue())
                .build());
        rabbitAdmin.declareExchange(ExchangeBuilder
                .directExchange(DlqEnum.SPRING_ERRORS_DLQ_EX.getValue())
                .build());
    }

    private void createQueues(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(INVENTORY_QUEUE.getValue())
                .deadLetterExchange(DlqEnum.SPRING_ERRORS_DLQ_EX.getValue())
                .deadLetterRoutingKey(INVENTORY_DLQ_KEY.getValue())
                .build());

        rabbitAdmin.declareQueue(QueueBuilder
                .durable(PAYMENT_QUEUE.getValue())
                .deadLetterExchange(DlqEnum.SPRING_ERRORS_DLQ_EX.getValue())
                .deadLetterRoutingKey(PAYMENT_DLQ_KEY.getValue())
                .build());
    }

    private void createDelayedQueues(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(INVENTORY_DELAYED_QUEUE.getValue())
                .deadLetterExchange(StringUtils.EMPTY)
                .deadLetterRoutingKey(INVENTORY_QUEUE.getValue())
                .build());

        rabbitAdmin.declareQueue(QueueBuilder
                .durable(PAYMENT_DELAYED_QUEUE.getValue())
                .deadLetterExchange(StringUtils.EMPTY)
                .deadLetterRoutingKey(PAYMENT_QUEUE.getValue())
                .build());
    }

    private void createDlqQueues(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(INVENTORY_DLQ_QUEUE.getValue())
                .build());

        rabbitAdmin.declareQueue(QueueBuilder
                .durable(PAYMENT_DLQ_QUEUE.getValue())
                .build());
    }

    private void doBinding(RabbitAdmin rabbitAdmin) {
        var inventoryBinding = new Binding(INVENTORY_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                SPRING_FANOUT_PRODUCT_EX.getValue(),
                StringUtils.EMPTY,
                null);

        var paymentBinding = new Binding(PAYMENT_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                SPRING_FANOUT_PRODUCT_EX.getValue(),
                StringUtils.EMPTY,
                null);

        rabbitAdmin.declareBinding(inventoryBinding);
        rabbitAdmin.declareBinding(paymentBinding);
    }

    private void doDlqBinding(RabbitAdmin rabbitAdmin) {
        var inventoryDlqBinding = new Binding(INVENTORY_DLQ_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                DlqEnum.SPRING_ERRORS_DLQ_EX.getValue(),
                INVENTORY_DLQ_KEY.getValue(),
                null);

        var paymentDlqBinding = new Binding(PAYMENT_DLQ_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                DlqEnum.SPRING_ERRORS_DLQ_EX.getValue(),
                PAYMENT_DLQ_KEY.getValue(),
                null);
        rabbitAdmin.declareBinding(inventoryDlqBinding);
        rabbitAdmin.declareBinding(paymentDlqBinding);
    }
}
