package com.frozendo.rabbit.sample.consumer.header;

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

import static com.frozendo.rabbit.sample.domain.enums.HeaderEnum.BIG_GIFT_QUEUE;

@Component
public class BigGiftConsumerService extends DefaultConsumer {

    Logger log = LoggerFactory.getLogger(BigGiftConsumerService.class);

    public BigGiftConsumerService(RabbitBaseConfig baseConfig) {
        super(baseConfig.getChannel());
        BaseConsumerInit.init(this, BIG_GIFT_QUEUE.getValue());
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        var product = (Product) SerializationUtils.deserialize(body);
        log.info("queue {}, value read = {}", BIG_GIFT_QUEUE.getValue(), product);
        log.info("message routingKey = {}", envelope.getRoutingKey());
        this.getChannel().basicAck(envelope.getDeliveryTag(), false);
    }
}
