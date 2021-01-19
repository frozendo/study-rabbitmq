package com.invillia.denver.sampleconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invillia.denver.sampleconsumer.config.QueueConstants;
import com.invillia.denver.sampleconsumer.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FanoutConsumerService {

    private final Logger logger = LoggerFactory.getLogger(FanoutConsumerService.class);
    private final ObjectMapper objectMapper;

    public FanoutConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = QueueConstants.SAMPLE_BEAN_FANOUT_QUEUE)
    public void sampleFanoutQueueConsumer(Message message) throws IOException {
        var product = objectMapper.readValue(message.getBody(), Product.class);
        logger.info(QueueConstants.LOG_MESSAGE, QueueConstants.SAMPLE_BEAN_FANOUT_QUEUE, product);
    }

    @RabbitListener(queues = QueueConstants.OTHER_SAMPLE_BEAN_FANOUT_QUEUE)
    public void otherFanoutQueueConsumer(Message message) throws IOException {
        var product = objectMapper.readValue(message.getBody(), Product.class);
        logger.info(QueueConstants.LOG_MESSAGE, QueueConstants.OTHER_SAMPLE_BEAN_FANOUT_QUEUE, product);
    }

}
