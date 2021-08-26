package com.frozendo.rabbit.sample.consumer.fanout;

import com.frozendo.rabbit.sample.config.RabbitBaseConfig;
import com.frozendo.rabbit.sample.consumer.BaseConsumerInit;
import com.frozendo.rabbit.sample.domain.Product;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.frozendo.rabbit.sample.domain.enums.FanoutEnum.INVENTORY_QUEUE;

@Component
public class InventoryConsumerService extends DefaultConsumer {

    Logger log = LoggerFactory.getLogger(InventoryConsumerService.class);

    public InventoryConsumerService(RabbitBaseConfig baseConfig) {
        super(baseConfig.getChannel());
        BaseConsumerInit.init(this, INVENTORY_QUEUE.getValue());
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        var product = (Product) SerializationUtils.deserialize(body);
        log.info("queue {}, value read = {}", INVENTORY_QUEUE.getValue(), product);
        this.getChannel().basicAck(envelope.getDeliveryTag(), false);
    }

}
