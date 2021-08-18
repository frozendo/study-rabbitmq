package com.frozendo.rabbit.sample.service;

import com.frozendo.rabbit.sample.config.RabbitBaseConfig;
import com.frozendo.rabbit.sample.domain.Product;
import com.rabbitmq.client.AMQP;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Optional;

public abstract class ExchangeService {

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
        try {
            channel.basicPublish(exchange,
                    key,
                    basicProperties,
                    product.toByteArray());
        } catch (IOException ex) {
            System.out.println("Rabbit exception");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("General exception");
            ex.printStackTrace();
        }
    };

}
