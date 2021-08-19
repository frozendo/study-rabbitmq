package com.frozendo.rabbit.errors.service;

import com.frozendo.rabbit.errors.config.RabbitBaseConfig;
import com.frozendo.rabbit.errors.domain.Product;
import com.frozendo.rabbit.errors.domain.enums.TopicEnum;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.MessageProperties;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicExchangeService extends ExchangeService {

    public TopicExchangeService(RabbitBaseConfig baseConfig) {
        super(baseConfig);
    }

    @Override
    public Optional<String> getRoutingKey(Product product) {
        if (product.getDepartment().toLowerCase().contains("sport") &&
                product.getDepartment().toLowerCase().contains("promotion")) {
            return Optional.of(TopicEnum.SPORT_PROMOTION_ROUTING_KEY.getValue());
        } else if (product.getDepartment().toLowerCase().contains("sport")) {
            return Optional.of(TopicEnum.SPORT_ROUTING_KEY.getValue());
        } else if (product.getDepartment().toLowerCase().contains("promotion")) {
            return Optional.of(TopicEnum.ELECTRONIC_PROMOTION_ROUTING_KEY.getValue());
        }
        return Optional.of(TopicEnum.GENERAL_ROUTING_KEY.getValue());
    }

    @Override
    String getExchange() {
        return TopicEnum.JAVA_TOPIC_PRODUCT_EX.getValue();
    }

    @Override
    AMQP.BasicProperties getBasicProperties(Product product) {
        return MessageProperties.PERSISTENT_TEXT_PLAIN;
    }
}
