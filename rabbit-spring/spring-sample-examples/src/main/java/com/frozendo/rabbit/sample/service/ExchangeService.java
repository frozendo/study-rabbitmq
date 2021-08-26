package com.frozendo.rabbit.sample.service;

import com.frozendo.rabbit.sample.domain.Product;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Optional;

public abstract class ExchangeService {

    Logger logger = LoggerFactory.getLogger(ExchangeService.class);

    protected final RabbitTemplate rabbitTemplate;

    protected ExchangeService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    abstract Optional<String> getRoutingKey(Product product);
    abstract String getExchange();

    public void sendMessage(Product product) {
        var routingKey = getRoutingKey(product)
                .orElse(StringUtils.EMPTY);
        var exchangeName = getExchange();
        logger.info("sending message to rabbit, exchange = {} routingKey = {}, product = {}", exchangeName, routingKey, product);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, product);
    }

}
