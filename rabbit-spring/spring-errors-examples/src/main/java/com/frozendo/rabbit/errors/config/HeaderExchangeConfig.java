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
import java.util.Map;

import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.BIG_GIFT_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.BIG_GIFT_DLQ_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.BIG_GIFT_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.BIG_GIFT_DLQ_KEY;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.SMALL_GIFT_DLQ_KEY;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.SPRING_HEADER_PRODUCT_EX;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.PRICE_HEADER;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.QUANTITY_HEADER;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.SMALL_GIFT_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.SMALL_GIFT_DLQ_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.SMALL_GIFT_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.X_MATCH_HEADER_KEY;

@Configuration
public class HeaderExchangeConfig {

    private final ConnectionFactory connectionFactory;

    public HeaderExchangeConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @PostConstruct
    public void createHeaderElements() {
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
                .headersExchange(SPRING_HEADER_PRODUCT_EX.getValue())
                .build());
        rabbitAdmin.declareExchange(ExchangeBuilder
                .directExchange(DlqEnum.SPRING_ERRORS_DLQ_EX.getValue())
                .build());
    }

    private void createQueues(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(BIG_GIFT_QUEUE.getValue())
                .deadLetterExchange(DlqEnum.SPRING_ERRORS_DLQ_EX.getValue())
                .deadLetterRoutingKey(BIG_GIFT_DLQ_KEY.getValue())
                .build());

        rabbitAdmin.declareQueue(QueueBuilder
                .durable(SMALL_GIFT_QUEUE.getValue())
                .deadLetterExchange(DlqEnum.SPRING_ERRORS_DLQ_EX.getValue())
                .deadLetterRoutingKey(SMALL_GIFT_DLQ_KEY.getValue())
                .build());
    }

    private void createDelayedQueues(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(BIG_GIFT_DELAYED_QUEUE.getValue())
                .deadLetterExchange(StringUtils.EMPTY)
                .deadLetterRoutingKey(BIG_GIFT_QUEUE.getValue())
                .build());

        rabbitAdmin.declareQueue(QueueBuilder
                .durable(SMALL_GIFT_DELAYED_QUEUE.getValue())
                .deadLetterExchange(StringUtils.EMPTY)
                .deadLetterRoutingKey(SMALL_GIFT_QUEUE.getValue())
                .build());
    }

    private void createDlqQueues(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(BIG_GIFT_DLQ_QUEUE.getValue())
                .build());

        rabbitAdmin.declareQueue(QueueBuilder
                .durable(SMALL_GIFT_DLQ_QUEUE.getValue())
                .build());
    }

    private void doBinding(RabbitAdmin rabbitAdmin) {
        Map<String, Object> bigGiftHeaders = Map.of(QUANTITY_HEADER.getValue(), Boolean.TRUE.toString(),
                PRICE_HEADER.getValue(), Boolean.TRUE.toString(),
                X_MATCH_HEADER_KEY.getValue(), "all");

        Map<String, Object> smallGiftHeaders = Map.of(QUANTITY_HEADER.getValue(), Boolean.TRUE.toString(),
                PRICE_HEADER.getValue(), Boolean.TRUE.toString(),
                X_MATCH_HEADER_KEY.getValue(), "any");

        var inventoryBinding = new Binding(BIG_GIFT_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                SPRING_HEADER_PRODUCT_EX.getValue(),
                StringUtils.EMPTY,
                bigGiftHeaders);

        var paymentBinding = new Binding(SMALL_GIFT_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                SPRING_HEADER_PRODUCT_EX.getValue(),
                StringUtils.EMPTY,
                smallGiftHeaders);

        rabbitAdmin.declareBinding(inventoryBinding);
        rabbitAdmin.declareBinding(paymentBinding);
    }

    private void doDlqBinding(RabbitAdmin rabbitAdmin) {
        var inventoryDlqBinding = new Binding(BIG_GIFT_DLQ_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                DlqEnum.SPRING_ERRORS_DLQ_EX.getValue(),
                BIG_GIFT_DLQ_KEY.getValue(),
                null);

        var paymentDlqBinding = new Binding(SMALL_GIFT_DLQ_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                DlqEnum.SPRING_ERRORS_DLQ_EX.getValue(),
                SMALL_GIFT_DLQ_KEY.getValue(),
                null);
        rabbitAdmin.declareBinding(inventoryDlqBinding);
        rabbitAdmin.declareBinding(paymentDlqBinding);
    }
}
