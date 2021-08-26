package com.frozendo.rabbit.sample.service;

import com.frozendo.rabbit.sample.config.RabbitBaseConfig;
import com.frozendo.rabbit.sample.domain.Product;
import com.rabbitmq.client.AMQP;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public abstract class ExchangeService {

    Logger logger = LoggerFactory.getLogger(ExchangeService.class);

    private final RabbitBaseConfig baseConfig;

    protected ExchangeService(RabbitBaseConfig baseConfig) {
        this.baseConfig = baseConfig;
    }

    abstract Optional<String> getRoutingKey(Product product);
    abstract String getExchange();
    abstract AMQP.BasicProperties getBasicProperties(Product product);

    public void sendMessage(Product product) {
        var channel = baseConfig.getChannel();
        var key = getRoutingKey(product)
                .orElse(StringUtils.EMPTY);
        var exchange = getExchange();
        var basicProperties = getBasicProperties(product);

        logger.info("sending message to rabbit, exchange = {} routingKey = {}, product = {}", exchange, key, product);

        try {
            channel.basicPublish(exchange,
                    key,
                    basicProperties,
                    product.toByteArray());
        } catch (IOException ex) {
            logger.error("Rabbit exception");
            ex.printStackTrace();
        } catch (Exception ex) {
            logger.error("General exception");
            ex.printStackTrace();
        }
    }

}
