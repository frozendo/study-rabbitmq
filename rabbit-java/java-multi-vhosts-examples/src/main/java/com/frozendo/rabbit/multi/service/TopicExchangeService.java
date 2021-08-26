package com.frozendo.rabbit.multi.service;

import com.frozendo.rabbit.multi.config.TopicExchangeConfig;
import com.frozendo.rabbit.multi.domain.Product;
import com.frozendo.rabbit.multi.domain.enums.TopicEnum;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TopicExchangeService implements ExchangeService {

    Logger logger = LoggerFactory.getLogger(TopicExchangeService.class);

    public void sendMessage(Product product) {
        var channel = TopicExchangeConfig.getChannel();
        var key = getRoutingKey(product);
        var exchange = getExchange();

        logger.info("sending message to rabbit, exchange = {} routingKey = {}, product = {}", exchange, key, product);

        try {
            channel.basicPublish(exchange,
                    key,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    product.toByteArray());
        } catch (IOException ex) {
            logger.error("Rabbit exception");
            ex.printStackTrace();
        } catch (Exception ex) {
            logger.error("General exception");
            ex.printStackTrace();
        }
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
        return TopicEnum.JAVA_TOPIC_PRODUCT_EX.getValue();
    }
}
