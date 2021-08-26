package com.frozendo.rabbit.sample.consumer.fanout;

import com.frozendo.rabbit.sample.domain.Product;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.frozendo.rabbit.sample.domain.enums.FanoutEnum.INVENTORY_QUEUE;

@Component
public class InventoryConsumerService {

    Logger log = LoggerFactory.getLogger(InventoryConsumerService.class);

    @RabbitListener(queues = "spring-sample-fanout-inventory-queue")
    public void consumer(Message message) {
        var product = (Product) SerializationUtils.deserialize(message.getBody());
        log.info("queue {}, value read = {}", INVENTORY_QUEUE.getValue(), product);
    }

}
