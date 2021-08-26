package com.frozendo.rabbit.multi.service;

import com.frozendo.rabbit.multi.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.frozendo.rabbit.multi.domain.enums.DirectEnum.SMALL_QUANTITY_KEY;
import static com.frozendo.rabbit.multi.domain.enums.DirectEnum.SPRING_DIRECT_PRODUCT_EX;
import static com.frozendo.rabbit.multi.domain.enums.DirectEnum.BIG_QUANTITY_KEY;

@Service
public class DirectExchangeService implements ExchangeService {

    Logger logger = LoggerFactory.getLogger(DirectExchangeService.class);

    private final RabbitTemplate rabbitTemplate;

    public DirectExchangeService(@Qualifier("directRabbitTemplate") RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMessage(Product product) {
        var routingKey = getRoutingKey(product);
        var exchangeName = getExchange();
        logger.info("sending message to rabbit, exchange = {} routingKey = {}, product = {}", exchangeName, routingKey, product);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, product);

    }

    public String getRoutingKey(Product product) {
        if (product.getQuantity() < 5) {
            return SMALL_QUANTITY_KEY.getValue();
        }
        return BIG_QUANTITY_KEY.getValue();
    }

    public String getExchange() {
        return SPRING_DIRECT_PRODUCT_EX.getValue();
    }
}
