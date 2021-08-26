package com.frozendo.rabbit.sample.consumer.direct;

import com.frozendo.rabbit.sample.domain.Product;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.BIG_QUANTITY_QUEUE;

@Component
public class BigQuantityConsumerService {

    Logger log = LoggerFactory.getLogger(BigQuantityConsumerService.class);

    @RabbitListener(queues = "spring-sample-direct-big-quantity-queue")
    public void consumer(Message message) {
        var product = (Product) SerializationUtils.deserialize(message.getBody());
        log.info("queue {}, value read = {}", BIG_QUANTITY_QUEUE.getValue(), product);
        log.info("message routingKey = {}", message.getMessageProperties().getReceivedRoutingKey());
    }
}
