package com.frozendo.rabbit.multi.config;

import com.frozendo.rabbit.multi.consumer.header.BigGiftConsumerService;
import com.frozendo.rabbit.multi.consumer.header.SmallGiftConsumerService;
import com.frozendo.rabbit.multi.domain.enums.HeaderEnum;
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
import java.util.Map;

import static com.frozendo.rabbit.multi.domain.enums.HeaderEnum.BIG_GIFT_QUEUE;
import static com.frozendo.rabbit.multi.domain.enums.HeaderEnum.SMALL_GIFT_QUEUE;
import static com.frozendo.rabbit.multi.domain.enums.HeaderEnum.SPRING_HEADER_PRODUCT_EX;

@Configuration
public class HeaderExchangeConfig {

    private final ConnectionFactory connectionFactory;
    private final BigGiftConsumerService bigGiftConsumerService;
    private final SmallGiftConsumerService smallGiftConsumerService;

    public HeaderExchangeConfig(@Qualifier("headerConnectionFactory") ConnectionFactory connectionFactory,
                                BigGiftConsumerService bigGiftConsumerService,
                                SmallGiftConsumerService smallGiftConsumerService) {
        this.connectionFactory = connectionFactory;
        this.bigGiftConsumerService = bigGiftConsumerService;
        this.smallGiftConsumerService = smallGiftConsumerService;
    }

    @PostConstruct
    public void createHeaderElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createExchange(rabbitAdmin);
        createQueues(rabbitAdmin);
        doBinding(rabbitAdmin);
    }

    @Bean
    public MessageListenerContainer bigGiftListener() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(BIG_GIFT_QUEUE.getValue());
        container.setMessageListener(bigGiftConsumerService);
        container.start();
        return container;
    }

    @Bean
    public MessageListenerContainer smallGiftListener() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(SMALL_GIFT_QUEUE.getValue());
        container.setMessageListener(smallGiftConsumerService);
        container.start();
        return container;
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
