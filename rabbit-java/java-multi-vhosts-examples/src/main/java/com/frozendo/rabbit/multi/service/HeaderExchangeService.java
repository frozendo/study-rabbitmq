package com.frozendo.rabbit.multi.service;

import com.frozendo.rabbit.multi.config.HeaderExchangeConfig;
import com.frozendo.rabbit.multi.domain.Product;
import com.frozendo.rabbit.multi.domain.enums.HeaderEnum;
import com.rabbitmq.client.AMQP;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class HeaderExchangeService implements ExchangeService {

    Logger logger = LoggerFactory.getLogger(HeaderExchangeService.class);

    @Override
    public void sendMessage(Product product) {
        var channel = HeaderExchangeConfig.getChannel();
        var exchange = getExchange();

        logger.info("sending message to rabbit, exchange = {} , product = {}", exchange, product);

        try {
            channel.basicPublish(exchange,
                    StringUtils.EMPTY,
                    getBasicProperties(product),
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
        return HeaderEnum.JAVA_HEADER_PRODUCT_EX.getValue();
    }

    private AMQP.BasicProperties getBasicProperties(Product product) {
        Map<String, Object> headers = Map.of(HeaderEnum.QUANTITY_HEADER.getValue(), haveGiftForTheQuantity(product.getQuantity()),
                HeaderEnum.PRICE_HEADER.getValue(), haveGiftForThePrice(product.getPrice()));

        return new AMQP.BasicProperties.Builder()
                .contentType("text/plain")
                .headers(headers)
                .build();
    }

    public String haveGiftForTheQuantity(Integer quantity) {
        if (quantity < 5) {
            return Boolean.FALSE.toString();
        }
        return Boolean.TRUE.toString();
    }

    public String haveGiftForThePrice(BigDecimal price) {
        if (BigDecimal.valueOf(50).compareTo(price) > 0) {
            return Boolean.FALSE.toString();
        }
        return Boolean.TRUE.toString();
    }
}
