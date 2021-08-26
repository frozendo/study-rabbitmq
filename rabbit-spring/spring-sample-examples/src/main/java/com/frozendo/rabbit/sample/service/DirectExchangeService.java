package com.frozendo.rabbit.sample.service;

import com.frozendo.rabbit.sample.domain.Product;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.BIG_QUANTITY_KEY;
import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.SMALL_QUANTITY_KEY;
import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.SPRING_DIRECT_PRODUCT_EX;

@Service
public class DirectExchangeService extends ExchangeService {

    public DirectExchangeService(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
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
        return SPRING_DIRECT_PRODUCT_EX.getValue();
    }
}
