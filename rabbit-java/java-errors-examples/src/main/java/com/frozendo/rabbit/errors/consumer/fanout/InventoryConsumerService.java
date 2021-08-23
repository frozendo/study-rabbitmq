package com.frozendo.rabbit.errors.consumer.fanout;

import com.frozendo.rabbit.errors.config.RabbitBaseConfig;
import com.frozendo.rabbit.errors.consumer.BaseConsumer;
import com.frozendo.rabbit.errors.domain.Product;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.INVENTORY_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.INVENTORY_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.PAYMENT_DELAYED_QUEUE;

@Component
public class InventoryConsumerService extends DefaultConsumer {

    Logger log = LoggerFactory.getLogger(InventoryConsumerService.class);

    private final BaseConsumer baseConsumer;

    public InventoryConsumerService(RabbitBaseConfig baseConfig, BaseConsumer baseConsumer) {
        super(baseConfig.getChannel());
        this.baseConsumer = baseConsumer;
        this.baseConsumer.init(this, INVENTORY_QUEUE.getValue());
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        var product = (Product) SerializationUtils.deserialize(body);
        log.info("queue {}, value read = {}", INVENTORY_QUEUE.getValue(), product);
        log.info("message routingKey = {}", envelope.getRoutingKey());
        var value = baseConsumer.getRandomNumber();
        if (value % 2 == 0) {
            log.info("sending ack to rabbit");
            this.getChannel().basicAck(envelope.getDeliveryTag(), false);
        } else {
            baseConsumer.rejectOrRequeueMessage(getChannel(), product, properties,
                    INVENTORY_DELAYED_QUEUE.getValue(), envelope.getDeliveryTag());
        }
    }

}
