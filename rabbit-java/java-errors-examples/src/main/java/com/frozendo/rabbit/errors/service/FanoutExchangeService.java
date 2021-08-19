package com.frozendo.rabbit.errors.service;

import com.frozendo.rabbit.errors.config.RabbitBaseConfig;
import com.frozendo.rabbit.errors.domain.Product;
import com.frozendo.rabbit.errors.domain.enums.FanoutEnum;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.MessageProperties;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FanoutExchangeService extends ExchangeService {

    protected FanoutExchangeService(RabbitBaseConfig baseConfig) {
        super(baseConfig);
    }

    @Override
    Optional<String> getRoutingKey(Product product) {
        return Optional.empty();
    }

    @Override
    String getExchange() {
        return FanoutEnum.JAVA_FANOUT_PRODUCT_EX.getValue();
    }

    @Override
    AMQP.BasicProperties getBasicProperties(Product product) {
        return MessageProperties.PERSISTENT_TEXT_PLAIN;
    }

}
