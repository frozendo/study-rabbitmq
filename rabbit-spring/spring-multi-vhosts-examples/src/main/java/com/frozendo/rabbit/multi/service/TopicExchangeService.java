package com.frozendo.rabbit.multi.service;

import com.frozendo.rabbit.multi.domain.Product;
import com.frozendo.rabbit.multi.domain.enums.TopicEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TopicExchangeService implements ExchangeService {

    Logger logger = LoggerFactory.getLogger(TopicExchangeService.class);

    private final RabbitTemplate rabbitTemplate;

    public TopicExchangeService(@Qualifier("topicRabbitTemplate") RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Product product) {
        var routingKey = getRoutingKey(product);
        var exchangeName = getExchange();
        logger.info("sending message to rabbit, exchange = {} routingKey = {}, product = {}", exchangeName, routingKey, product);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, product);
    }

    private String getRoutingKey(Product product) {
        if (product.getDepartment().toLowerCase().contains("sport") &&
                product.getDepartment().toLowerCase().contains("promotion")) {
            return TopicEnum.SPORT_PROMOTION_ROUTING_KEY.getValue();
        } else if (product.getDepartment().toLowerCase().contains("sport")) {
            return TopicEnum.SPORT_ROUTING_KEY.getValue();
        } else if (product.getDepartment().toLowerCase().contains("promotion")) {
            return TopicEnum.ELECTRONIC_PROMOTION_ROUTING_KEY.getValue();
        }
        return TopicEnum.GENERAL_ROUTING_KEY.getValue();
    }

    private String getExchange() {
        return TopicEnum.SPRING_TOPIC_PRODUCT_EX.getValue();
    }
}
