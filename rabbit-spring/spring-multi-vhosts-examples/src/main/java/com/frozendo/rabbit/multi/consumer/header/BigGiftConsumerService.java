package com.frozendo.rabbit.multi.consumer.header;

import com.frozendo.rabbit.multi.domain.Product;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import static com.frozendo.rabbit.multi.domain.enums.HeaderEnum.BIG_GIFT_QUEUE;

@Component
public class BigGiftConsumerService implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(BigGiftConsumerService.class);

    @Override
    public void onMessage(Message message) {
        var product = (Product) SerializationUtils.deserialize(message.getBody());
        logger.info("queue {}, value read = {}", BIG_GIFT_QUEUE.getValue(), product);
        logger.info("headers = {}", message.getMessageProperties().getHeaders());
    }
}
