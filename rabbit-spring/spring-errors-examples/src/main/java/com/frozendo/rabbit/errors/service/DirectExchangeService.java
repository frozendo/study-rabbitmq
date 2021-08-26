package com.frozendo.rabbit.errors.service;

import com.frozendo.rabbit.errors.domain.Product;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.frozendo.rabbit.errors.domain.enums.DirectEnum.SMALL_QUANTITY_KEY;
import static com.frozendo.rabbit.errors.domain.enums.DirectEnum.SPRING_DIRECT_PRODUCT_EX;
import static com.frozendo.rabbit.errors.domain.enums.DirectEnum.BIG_QUANTITY_KEY;

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
