package com.invillia.denver.sampleconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invillia.denver.sampleconsumer.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DirectConsumerService {

    public static final String DIRECT_EXAMPLE_QUEUE = "example-direct-queue";
    public static final String OTHER_DIRECT_EXAMPLE_QUEUE = "other-example-direct-queue";
    public static final String LOG_MESSAGE = "Consume QUEUE {}, with ROUTING_KEY = {}, with VALUE = {}";

    private Logger LOG = LoggerFactory.getLogger(DirectConsumerService.class);

    private final ObjectMapper objectMapper;

    public DirectConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = DIRECT_EXAMPLE_QUEUE)
    public void exampleDirectQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            var routingKey = message.getMessageProperties().getReceivedRoutingKey();
            LOG.info(LOG_MESSAGE, DIRECT_EXAMPLE_QUEUE, routingKey, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }

    @RabbitListener(queues = OTHER_DIRECT_EXAMPLE_QUEUE)
    public void otherDirectQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            var routingKey = message.getMessageProperties().getReceivedRoutingKey();
            LOG.info(LOG_MESSAGE, OTHER_DIRECT_EXAMPLE_QUEUE, routingKey, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }
}
