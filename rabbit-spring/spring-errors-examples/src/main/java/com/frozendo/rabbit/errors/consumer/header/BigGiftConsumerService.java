package com.frozendo.rabbit.errors.consumer.header;

import com.frozendo.rabbit.errors.consumer.BaseConsumer;
import com.frozendo.rabbit.errors.domain.Product;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.BIG_GIFT_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.BIG_GIFT_QUEUE;

@Component
public class BigGiftConsumerService {

    Logger log = LoggerFactory.getLogger(BigGiftConsumerService.class);

    private final BaseConsumer baseConsumer;

    public BigGiftConsumerService(BaseConsumer baseConsumer) {
        this.baseConsumer = baseConsumer;
    }

    @RabbitListener(queues = "spring-errors-header-big-gift-queue")
    public void consumer(Message message) {
        var product = (Product) SerializationUtils.deserialize(message.getBody());
        log.info("queue {}, value read = {}", BIG_GIFT_QUEUE.getValue(), product);
        log.info("headers = {}", message.getMessageProperties().getHeaders());
        var value = baseConsumer.getRandomNumber();
        if (value % 2 != 0) {
            baseConsumer.rejectOrRequeueMessage(BIG_GIFT_DELAYED_QUEUE.getValue(), message);
        }
    }
}
