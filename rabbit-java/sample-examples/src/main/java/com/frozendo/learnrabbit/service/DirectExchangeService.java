package com.frozendo.learnrabbit.service;

import com.frozendo.learnrabbit.config.RabbitBaseConfig;
import com.frozendo.learnrabbit.domain.Product;
import com.rabbitmq.client.MessageProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.frozendo.learnrabbit.domain.DirectEnum.EVEN_VALUE_KEY;
import static com.frozendo.learnrabbit.domain.DirectEnum.JAVA_DIRECT_PRODUCT_EX;
import static com.frozendo.learnrabbit.domain.DirectEnum.ODD_VALUE_KEY;

@Service
public class DirectExchangeService implements ExchangeService {

    private final RabbitBaseConfig baseConfig;

    public DirectExchangeService(RabbitBaseConfig baseConfig) {
        this.baseConfig = baseConfig;
    }

    public void sendMessage(Product product) {
        var channel = baseConfig.getChannel();
        var key = getRoutingKey(product.getQuantity());
        try {
            channel.basicPublish(JAVA_DIRECT_PRODUCT_EX.getValue(),
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

    public String getRoutingKey(Integer quantity) {
        if (quantity % 2 == 0) {
            return EVEN_VALUE_KEY.getValue();
        }
        return ODD_VALUE_KEY.getValue();
    }

}
