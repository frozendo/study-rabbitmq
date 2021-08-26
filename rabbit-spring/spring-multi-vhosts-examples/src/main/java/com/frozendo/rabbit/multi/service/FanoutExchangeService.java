package com.frozendo.rabbit.multi.service;

import com.frozendo.rabbit.multi.domain.Product;
import com.frozendo.rabbit.multi.domain.enums.FanoutEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FanoutExchangeService implements ExchangeService {

    Logger logger = LoggerFactory.getLogger(FanoutExchangeService.class);

    private final RabbitTemplate rabbitTemplate;

    public FanoutExchangeService(@Qualifier("fanoutRabbitTemplate") RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMessage(Product product) {
        var exchangeName = getExchange();
        logger.info("sending message to rabbit, exchange = {}, product = {}", exchangeName, product);
        rabbitTemplate.convertAndSend(exchangeName, StringUtils.EMPTY, product);
    }

    private String getExchange() {
        return FanoutEnum.SPRING_FANOUT_PRODUCT_EX.getValue();
    }

}
