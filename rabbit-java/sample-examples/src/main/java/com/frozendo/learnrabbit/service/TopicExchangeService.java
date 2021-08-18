package com.frozendo.learnrabbit.service;

import com.frozendo.learnrabbit.config.RabbitBaseConfig;
import com.frozendo.learnrabbit.domain.Product;
import com.rabbitmq.client.MessageProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.frozendo.learnrabbit.domain.TopicEnum.ELECTRONIC_PROMOTION_ROUTING_KEY;
import static com.frozendo.learnrabbit.domain.TopicEnum.GENERAL_ROUTING_KEY;
import static com.frozendo.learnrabbit.domain.TopicEnum.JAVA_TOPIC_PRODUCT_EX;
import static com.frozendo.learnrabbit.domain.TopicEnum.SPORT_PROMOTION_ROUTING_KEY;
import static com.frozendo.learnrabbit.domain.TopicEnum.SPORT_ROUTING_KEY;

@Service
public class TopicExchangeService implements ExchangeService {

    private final RabbitBaseConfig baseConfig;

    public TopicExchangeService(RabbitBaseConfig baseConfig) {
        this.baseConfig = baseConfig;
    }

    public void sendMessage(Product product) {
        var channel = baseConfig.getChannel();
        var key = getRoutingKey(product.getDepartment());
        try {
            channel.basicPublish(JAVA_TOPIC_PRODUCT_EX.getValue(),
                    key,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    product.toByteArray());
        } catch (IOException ex) {
            System.out.println("Rabbit exception");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("General exception");
            ex.printStackTrace();
        }
    }

    public String getRoutingKey(String department) {
        if (department.toLowerCase().contains("sport") &&
                department.toLowerCase().contains("promotion")) {
            return SPORT_PROMOTION_ROUTING_KEY.getValue();
        } else if (department.toLowerCase().contains("sport")) {
            return SPORT_ROUTING_KEY.getValue();
        } else if (department.toLowerCase().contains("promotion")) {
            return ELECTRONIC_PROMOTION_ROUTING_KEY.getValue();
        }
        return GENERAL_ROUTING_KEY.getValue();
    }
}
