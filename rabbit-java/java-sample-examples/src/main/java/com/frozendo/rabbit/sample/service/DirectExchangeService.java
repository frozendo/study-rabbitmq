package com.frozendo.rabbit.sample.service;

import com.frozendo.rabbit.sample.config.RabbitBaseConfig;
import com.frozendo.rabbit.sample.domain.Product;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.MessageProperties;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.SMALL_QUANTITY_KEY;
import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.JAVA_DIRECT_PRODUCT_EX;
import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.BIG_QUANTITY_KEY;

@Service
public class DirectExchangeService extends ExchangeService {

    public DirectExchangeService(RabbitBaseConfig baseConfig) {
        super(baseConfig);
    }

    @Override
    Optional<String> getRoutingKey(Product product) {
        if (product.getQuantity() < 5) {
            return Optional.of(SMALL_QUANTITY_KEY.getValue());
        }
        return Optional.of(BIG_QUANTITY_KEY.getValue());
    }

    @Override
    String getExchange() {
        return JAVA_DIRECT_PRODUCT_EX.getValue();
    }

    @Override
    AMQP.BasicProperties getBasicProperties(Product product) {
        return MessageProperties.PERSISTENT_TEXT_PLAIN;
    }
}
