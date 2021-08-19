package com.frozendo.rabbit.multi.service;

import com.frozendo.rabbit.multi.config.DirectExchangeConfig;
import com.frozendo.rabbit.multi.config.TopicExchangeConfig;
import com.frozendo.rabbit.multi.domain.Product;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.MessageProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static com.frozendo.rabbit.multi.domain.enums.DirectEnum.SMALL_QUANTITY_KEY;
import static com.frozendo.rabbit.multi.domain.enums.DirectEnum.JAVA_DIRECT_PRODUCT_EX;
import static com.frozendo.rabbit.multi.domain.enums.DirectEnum.BIG_QUANTITY_KEY;

@Service
public class DirectExchangeService implements ExchangeService {

    Logger logger = LoggerFactory.getLogger(DirectExchangeService.class);

    @Override
    public void sendMessage(Product product) {
        var channel = DirectExchangeConfig.getChannel();
        var key = getRoutingKey(product);
        var exchange = getExchange();

        logger.info("sending message to rabbit, exchange = {} routingKey = {}, product = {}", exchange, key, product);

        try {
            channel.basicPublish(exchange,
                    key,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    product.toByteArray());
        } catch (IOException ex) {
            System.out.println("Rabbit exception");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("General exception");
            ex.printStackTrace();
        }

    }

    public String getRoutingKey(Product product) {
        if (product.getQuantity() < 5) {
            return SMALL_QUANTITY_KEY.getValue();
        }
        return BIG_QUANTITY_KEY.getValue();
    }

    public String getExchange() {
        return JAVA_DIRECT_PRODUCT_EX.getValue();
    }
}
