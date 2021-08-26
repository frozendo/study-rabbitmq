package com.frozendo.rabbit.errors.consumer.topic;

import com.frozendo.rabbit.errors.consumer.BaseConsumer;
import com.frozendo.rabbit.errors.domain.Product;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.PROMOTION_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.PROMOTION_QUEUE;

@Component
public class PromotionConsumerService {

    Logger log = LoggerFactory.getLogger(PromotionConsumerService.class);

    private final BaseConsumer baseConsumer;

    public PromotionConsumerService(BaseConsumer baseConsumer) {
        this.baseConsumer = baseConsumer;
    }

    @RabbitListener(queues = "spring-errors-topic-promotion-queue")
    public void consumer(Message message) {
        var product = (Product) SerializationUtils.deserialize(message.getBody());
        log.info("queue {}, value read = {}", PROMOTION_QUEUE.getValue(), product);
        log.info("message routingKey = {}", message.getMessageProperties().getReceivedRoutingKey());
        var value = baseConsumer.getRandomNumber();
        if (value % 2 != 0) {
            baseConsumer.rejectOrRequeueMessage(PROMOTION_DELAYED_QUEUE.getValue(), message);
        }
    }

}
