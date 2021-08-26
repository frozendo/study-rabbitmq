package com.frozendo.rabbit.errors.service;

import com.frozendo.rabbit.errors.domain.Product;
import com.frozendo.rabbit.errors.domain.enums.HeaderEnum;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.PRICE_HEADER;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.QUANTITY_HEADER;

@Service
public class HeaderExchangeService extends ExchangeService {

    public HeaderExchangeService(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Override
    Optional<String> getRoutingKey(Product product) {
        return Optional.empty();
    }

    @Override
    String getExchange() {
        return HeaderEnum.SPRING_HEADER_PRODUCT_EX.getValue();
    }

    @Override
    MessagePostProcessor getHeaders(Product product) {
        return m -> {
            m.getMessageProperties().getHeaders().put(QUANTITY_HEADER.getValue(), haveGiftForTheQuantity(product.getQuantity()));
            m.getMessageProperties().getHeaders().put(PRICE_HEADER.getValue(), haveGiftForThePrice(product.getPrice()));
            return m;
        };
    }

    private String haveGiftForTheQuantity(Integer quantity) {
        if (quantity < 5) {
            return Boolean.FALSE.toString();
        }
        return Boolean.TRUE.toString();
    }

    private String haveGiftForThePrice(BigDecimal price) {
        if (BigDecimal.valueOf(50).compareTo(price) > 0) {
            return Boolean.FALSE.toString();
        }
        return Boolean.TRUE.toString();
    }
}
