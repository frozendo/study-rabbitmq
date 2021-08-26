package com.frozendo.rabbit.errors.service;

import com.frozendo.rabbit.errors.domain.Product;
import com.frozendo.rabbit.errors.domain.enums.TopicEnum;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicExchangeService extends ExchangeService {

    public TopicExchangeService(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Override
    public Optional<String> getRoutingKey(Product product) {
        if (product.getDepartment().toLowerCase().contains("sport") &&
                product.getDepartment().toLowerCase().contains("promotion")) {
            return Optional.of(TopicEnum.SPORT_PROMOTION_ROUTING_KEY.getValue());
        } else if (product.getDepartment().toLowerCase().contains("sport")) {
            return Optional.of(TopicEnum.SPORT_ROUTING_KEY.getValue());
        } else if (product.getDepartment().toLowerCase().contains("promotion")) {
            return Optional.of(TopicEnum.ELECTRONIC_PROMOTION_ROUTING_KEY.getValue());
        }
        return Optional.of(TopicEnum.GENERAL_ROUTING_KEY.getValue());
    }

    @Override
    String getExchange() {
        return TopicEnum.SPRING_TOPIC_PRODUCT_EX.getValue();
    }
}
