package com.invillia.denver.retrydlqconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invillia.denver.retrydlqconsumer.config.QueueConstants;
import com.invillia.denver.retrydlqconsumer.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LoopExampleConsumerService {

    private final Logger logger = LoggerFactory.getLogger(LoopExampleConsumerService.class);
    private final ObjectMapper objectMapper;

    public LoopExampleConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = QueueConstants.LOOP_EXAMPLE_QUEUE)
    public void sampleFanoutQueueConsumer(Message message) throws IOException, InterruptedException {
        var product = objectMapper.readValue(message.getBody(), Product.class);
        var routingKey = message.getMessageProperties().getReceivedRoutingKey();
        logger.info(QueueConstants.LOG_MESSAGE_ROUTING_KEY, QueueConstants.LOOP_EXAMPLE_QUEUE, routingKey, product);

        Thread.sleep(5000);
        throw new RuntimeException("Error Loop Message");
    }
}
