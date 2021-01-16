package com.invillia.denver.sampleconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invillia.denver.sampleconsumer.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FanoutConsumerService {

    public static final String EXAMPLE_FANOUT_QUEUE = "example-fanout-queue";
    public static final String OTHER_EXAMPLE_FANOUT_QUEUE = "other-example-fanout-queue";
    public static final String LOG_MESSAGE = "Consume QUEUE {}, with VALUE = {}";

    private Logger LOG = LoggerFactory.getLogger(FanoutConsumerService.class);

    private final ObjectMapper objectMapper;

    public FanoutConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = EXAMPLE_FANOUT_QUEUE)
    public void exampleFanoutQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            LOG.info(LOG_MESSAGE, EXAMPLE_FANOUT_QUEUE, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }

    @RabbitListener(queues = OTHER_EXAMPLE_FANOUT_QUEUE)
    public void otherFanoutQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            LOG.info(LOG_MESSAGE, OTHER_EXAMPLE_FANOUT_QUEUE, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }

}
