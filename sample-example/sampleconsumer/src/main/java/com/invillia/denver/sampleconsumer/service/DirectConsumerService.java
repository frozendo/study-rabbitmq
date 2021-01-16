package com.invillia.denver.sampleconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invillia.denver.sampleconsumer.model.ProductConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DirectConsumerService {

    public static final String SAMPLE_DIRECT_QUEUE = "sample-direct-queue";
    public static final String OTHER_SAMPLE_DIRECT_QUEUE = "other-sample-direct-queue";
    public static final String LOG_MESSAGE = "Consume QUEUE {}, with ROUTING_KEY = {}, with VALUE = {}";

    private Logger LOG = LoggerFactory.getLogger(DirectConsumerService.class);

    private final ObjectMapper objectMapper;

    public DirectConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = SAMPLE_DIRECT_QUEUE)
    public void sampleDirectQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), ProductConsumer.class);
            var routingKey = message.getMessageProperties().getReceivedRoutingKey();
            LOG.info(LOG_MESSAGE, SAMPLE_DIRECT_QUEUE, routingKey, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }

    @RabbitListener(queues = OTHER_SAMPLE_DIRECT_QUEUE)
    public void otherDirectQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), ProductConsumer.class);
            var routingKey = message.getMessageProperties().getReceivedRoutingKey();
            LOG.info(LOG_MESSAGE, OTHER_SAMPLE_DIRECT_QUEUE, routingKey, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }
}
