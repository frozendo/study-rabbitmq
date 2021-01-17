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
public class TopicConsumerService {

    public static final String MULTI_VHOST_TOPIC_QUEUE = "multi-vhost-topic-queue";
    public static final String OTHER_MULTI_VHOST_TOPIC_QUEUE = "other-multi-vhost-topic-queue";
    public static final String THIRD_MULTI_VHOST_TOPIC_QUEUE = "third-multi-vhost-topic-queue";
    public static final String TOPIC_LISTENER_CONTAINER = "topicListenerContainer";
    public static final String LOG_MESSAGE = "Consume QUEUE {}, with ROUTING_KEY = {}, with VALUE = {}";

    private Logger LOG = LoggerFactory.getLogger(TopicConsumerService.class);

    private final ObjectMapper objectMapper;

    public TopicConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = MULTI_VHOST_TOPIC_QUEUE, containerFactory = TOPIC_LISTENER_CONTAINER)
    public void multiVhostTopicQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            var routingKey = message.getMessageProperties().getReceivedRoutingKey();
            LOG.info(LOG_MESSAGE, MULTI_VHOST_TOPIC_QUEUE, routingKey, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }

    @RabbitListener(queues = OTHER_MULTI_VHOST_TOPIC_QUEUE, containerFactory = TOPIC_LISTENER_CONTAINER)
    public void otherTopicQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            var routingKey = message.getMessageProperties().getReceivedRoutingKey();
            LOG.info(LOG_MESSAGE, OTHER_MULTI_VHOST_TOPIC_QUEUE, routingKey, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }

    @RabbitListener(queues = THIRD_MULTI_VHOST_TOPIC_QUEUE, containerFactory = TOPIC_LISTENER_CONTAINER)
    public void thirdTopicQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            var routingKey = message.getMessageProperties().getReceivedRoutingKey();
            LOG.info(LOG_MESSAGE, THIRD_MULTI_VHOST_TOPIC_QUEUE, routingKey, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }
}
