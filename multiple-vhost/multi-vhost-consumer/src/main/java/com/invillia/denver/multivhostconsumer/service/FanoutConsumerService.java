package com.invillia.denver.multivhostconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invillia.denver.multivhostconsumer.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FanoutConsumerService {

    public static final String MULTI_VHOST_FANOUT_QUEUE = "multi-vhost-fanout-queue";
    public static final String OTHER_MULTI_VHOST_FANOUT_QUEUE = "other-multi-vhost-fanout-queue";
    public static final String PRIMARY_LISTENER_CONTAINER = "primaryListenerContainer";
    public static final String LOG_MESSAGE = "Consume QUEUE {}, with VALUE = {}";

    private Logger LOG = LoggerFactory.getLogger(FanoutConsumerService.class);

    private final ObjectMapper objectMapper;

    public FanoutConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = MULTI_VHOST_FANOUT_QUEUE, containerFactory = PRIMARY_LISTENER_CONTAINER)
    public void multiVhostFanoutQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            LOG.info(LOG_MESSAGE, MULTI_VHOST_FANOUT_QUEUE, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }

    @RabbitListener(queues = OTHER_MULTI_VHOST_FANOUT_QUEUE, containerFactory = PRIMARY_LISTENER_CONTAINER)
    public void otherFanoutQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            LOG.info(LOG_MESSAGE, OTHER_MULTI_VHOST_FANOUT_QUEUE, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }
}
