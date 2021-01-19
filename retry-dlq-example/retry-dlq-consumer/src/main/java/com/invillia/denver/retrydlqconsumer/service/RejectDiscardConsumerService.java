package com.invillia.denver.retrydlqconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invillia.denver.retrydlqconsumer.config.QueueConstants;
import com.invillia.denver.retrydlqconsumer.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RejectDiscardConsumerService {

    private Logger LOG = LoggerFactory.getLogger(RejectDiscardConsumerService.class);

    private final ObjectMapper objectMapper;

    public RejectDiscardConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = QueueConstants.REJECT_DISCARD_QUEUE)
    public void sampleFanoutQueueConsumer(Message message) throws IOException {
        var product = objectMapper.readValue(message.getBody(), Product.class);
        var routingKey = message.getMessageProperties().getReceivedRoutingKey();
        LOG.info(QueueConstants.LOG_MESSAGE_ROUTING_KEY, QueueConstants.REJECT_DISCARD_QUEUE, routingKey, product);
        throw new AmqpRejectAndDontRequeueException("Error Loop Message");
    }
}
