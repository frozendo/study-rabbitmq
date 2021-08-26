package com.frozendo.rabbit.multi.service;

import com.frozendo.rabbit.multi.domain.Product;
import com.frozendo.rabbit.multi.domain.enums.HeaderEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.frozendo.rabbit.multi.domain.enums.HeaderEnum.PRICE_HEADER;
import static com.frozendo.rabbit.multi.domain.enums.HeaderEnum.QUANTITY_HEADER;

@Service
public class HeaderExchangeService implements ExchangeService {

    Logger logger = LoggerFactory.getLogger(HeaderExchangeService.class);

    private final RabbitTemplate rabbitTemplate;

    public HeaderExchangeService(@Qualifier("headerRabbitTemplate") RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMessage(Product product) {
        var exchangeName = getExchange();
        logger.info("sending message to rabbit, exchange = {}, product = {}", exchangeName, product);
        rabbitTemplate.convertAndSend(exchangeName, StringUtils.EMPTY, product, getHeaders(product));
    }

    private String getExchange() {
        return HeaderEnum.SPRING_HEADER_PRODUCT_EX.getValue();
    }

    private MessagePostProcessor getHeaders(Product product) {
        return m -> {
            m.getMessageProperties().getHeaders().put(QUANTITY_HEADER.getValue(), haveGiftForTheQuantity(product.getQuantity()));
            m.getMessageProperties().getHeaders().put(PRICE_HEADER.getValue(), haveGiftForThePrice(product.getPrice()));
            return m;
        };
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
