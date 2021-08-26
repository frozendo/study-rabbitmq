package com.frozendo.rabbit.sample.config;

import com.frozendo.rabbit.sample.domain.enums.HeaderEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

import static com.frozendo.rabbit.sample.domain.enums.HeaderEnum.BIG_GIFT_QUEUE;
import static com.frozendo.rabbit.sample.domain.enums.HeaderEnum.SPRING_HEADER_PRODUCT_EX;
import static com.frozendo.rabbit.sample.domain.enums.HeaderEnum.SMALL_GIFT_QUEUE;

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
        doBinding(rabbitAdmin);
    }

    private void createExchange(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .headersExchange(SPRING_HEADER_PRODUCT_EX.getValue())
                .build());
    }

    private void createQueues(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(BIG_GIFT_QUEUE.getValue())
                .build());
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(SMALL_GIFT_QUEUE.getValue())
                .build());
    }

    private void doBinding(RabbitAdmin rabbitAdmin) {
        Map<String, Object> bigGiftHeaders = Map.of(HeaderEnum.QUANTITY_HEADER.getValue(), Boolean.TRUE.toString(),
                HeaderEnum.PRICE_HEADER.getValue(), Boolean.TRUE.toString(),
                HeaderEnum.X_MATCH_HEADER_KEY.getValue(), "all");

        Map<String, Object> smallGiftHeaders = Map.of(HeaderEnum.QUANTITY_HEADER.getValue(), Boolean.TRUE.toString(),
                HeaderEnum.PRICE_HEADER.getValue(), Boolean.TRUE.toString(),
                HeaderEnum.X_MATCH_HEADER_KEY.getValue(), "any");

        var bigGiftBinding = new Binding(BIG_GIFT_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                SPRING_HEADER_PRODUCT_EX.getValue(),
                StringUtils.EMPTY,
                bigGiftHeaders);

        var smallGiftBinding = new Binding(SMALL_GIFT_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                SPRING_HEADER_PRODUCT_EX.getValue(),
                StringUtils.EMPTY,
                smallGiftHeaders);

        rabbitAdmin.declareBinding(bigGiftBinding);
        rabbitAdmin.declareBinding(smallGiftBinding);
    }
}
