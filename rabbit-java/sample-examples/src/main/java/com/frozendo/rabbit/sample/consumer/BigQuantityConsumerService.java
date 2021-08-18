package com.frozendo.rabbit.sample.consumer;

import com.frozendo.rabbit.sample.config.RabbitBaseConfig;
import com.frozendo.rabbit.sample.domain.Product;
import com.frozendo.rabbit.sample.domain.enums.DirectEnum;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class BigQuantityConsumerService extends DefaultConsumer {

    public BigQuantityConsumerService(RabbitBaseConfig baseConfig) {
        super(baseConfig.getChannel());
    }

    @PostConstruct
    public void init() {
        try {
            this.getChannel().basicConsume(DirectEnum.BIG_QUANTITY_QUEUE.getValue(), false, "myConsumeTag", this);
        } catch (IOException ex) {
            System.out.println("Rabbit error");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("General error");
            ex.printStackTrace();
        }
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String routingKey = envelope.getRoutingKey();
        String contentType = properties.getContentType();
        long deliveryTag = envelope.getDeliveryTag();
        var test = (Product) SerializationUtils.deserialize(body);
        this.getChannel().basicAck(deliveryTag, false);
    }
}
