package com.frozendo.rabbit.errors.service;

import com.frozendo.rabbit.errors.config.RabbitBaseConfig;
import com.frozendo.rabbit.errors.domain.Product;
import com.frozendo.rabbit.errors.domain.enums.HeaderEnum;
import com.rabbitmq.client.AMQP;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class HeaderExchangeService extends ExchangeService {

    protected HeaderExchangeService(RabbitBaseConfig baseConfig) {
        super(baseConfig);
    }

    @Override
    Optional<String> getRoutingKey(Product product) {
        return Optional.empty();
    }

    @Override
    String getExchange() {
        return HeaderEnum.JAVA_HEADER_PRODUCT_EX.getValue();
    }

    @Override
    AMQP.BasicProperties getBasicProperties(Product product) {
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
