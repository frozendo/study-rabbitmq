package com.frozendo.rabbit.multi.service;

import com.frozendo.rabbit.multi.config.FanoutExchangeConfig;
import com.frozendo.rabbit.multi.domain.Product;
import com.frozendo.rabbit.multi.domain.enums.FanoutEnum;
import com.rabbitmq.client.MessageProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FanoutExchangeService implements ExchangeService {

    Logger logger = LoggerFactory.getLogger(FanoutExchangeService.class);

    @Override
    public void sendMessage(Product product) {
        var channel = FanoutExchangeConfig.getChannel();
        var exchange = getExchange();

        logger.info("sending message to rabbit, exchange = {} , product = {}", exchange, product);

        try {
            channel.basicPublish(exchange,
                    StringUtils.EMPTY,
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

    private String getExchange() {
        return FanoutEnum.JAVA_FANOUT_PRODUCT_EX.getValue();
    }

}
