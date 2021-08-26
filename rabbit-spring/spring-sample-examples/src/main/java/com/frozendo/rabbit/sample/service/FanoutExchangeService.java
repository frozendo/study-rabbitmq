package com.frozendo.rabbit.sample.service;

import com.frozendo.rabbit.sample.domain.Product;
import com.frozendo.rabbit.sample.domain.enums.FanoutEnum;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FanoutExchangeService extends ExchangeService {

    public FanoutExchangeService(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Override
    Optional<String> getRoutingKey(Product product) {
        return Optional.empty();
    }

    @Override
    String getExchange() {
        return FanoutEnum.SPRING_FANOUT_PRODUCT_EX.getValue();
    }

}
