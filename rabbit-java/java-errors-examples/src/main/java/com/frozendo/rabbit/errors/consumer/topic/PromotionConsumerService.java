package com.frozendo.rabbit.errors.consumer.topic;

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

import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.PROMOTION_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.PROMOTION_QUEUE;

@Component
public class PromotionConsumerService extends DefaultConsumer {

    Logger log = LoggerFactory.getLogger(PromotionConsumerService.class);

    private final BaseConsumer baseConsumer;

    public PromotionConsumerService(RabbitBaseConfig baseConfig, BaseConsumer baseConsumer) {
        super(baseConfig.getChannel());
        this.baseConsumer = baseConsumer;
        baseConsumer.init(this, PROMOTION_QUEUE.getValue());
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        var product = (Product) SerializationUtils.deserialize(body);
        log.info("queue {}, value read = {}", PROMOTION_QUEUE.getValue(), product);
        log.info("message routingKey = {}", envelope.getRoutingKey());
        var value = baseConsumer.getRandomNumber();
        if (value % 2 == 0) {
            log.info("sending ack to rabbit");
            this.getChannel().basicAck(envelope.getDeliveryTag(), false);
        } else {
            baseConsumer.rejectOrRequeueMessage(this.getChannel(), product, properties,
                    PROMOTION_DELAYED_QUEUE.getValue(), envelope.getDeliveryTag());
        }
    }

}
