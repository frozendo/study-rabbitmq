package com.frozendo.rabbit.errors.service;

import com.frozendo.rabbit.errors.domain.Product;
import com.rabbitmq.client.AMQP;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;
import java.util.Optional;

public abstract class ExchangeService {

    Logger logger = LoggerFactory.getLogger(ExchangeService.class);

    private final RabbitTemplate rabbitTemplate;

    protected ExchangeService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    abstract Optional<String> getRoutingKey(Product product);
    abstract String getExchange();

    MessagePostProcessor getHeaders(Product product) {
        return m -> m;
    }

    public void sendMessage(Product product) {
        var routingKey = getRoutingKey(product)
                .orElse(StringUtils.EMPTY);
        var exchangeName = getExchange();
        logger.info("sending message to rabbit, exchange = {} routingKey = {}, product = {}", exchangeName, routingKey, product);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, product, getHeaders(product));
    }

}
