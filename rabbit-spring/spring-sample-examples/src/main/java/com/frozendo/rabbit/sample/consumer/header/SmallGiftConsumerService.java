package com.frozendo.rabbit.sample.consumer.header;

import com.frozendo.rabbit.sample.domain.Product;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.frozendo.rabbit.sample.domain.enums.HeaderEnum.SMALL_GIFT_QUEUE;

@Component
public class SmallGiftConsumerService {

    Logger log = LoggerFactory.getLogger(SmallGiftConsumerService.class);

    @RabbitListener(queues = "spring-sample-header-small-gift-queue")
    public void consumer(Message message) {
        var product = (Product) SerializationUtils.deserialize(message.getBody());
        log.info("queue {}, value read = {}", SMALL_GIFT_QUEUE.getValue(), product);
        log.info("headers = {}", message.getMessageProperties().getHeaders());
    }

}
