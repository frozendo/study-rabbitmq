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
public class TopicConsumerService {

    public static final String EXAMPLE_TOPIC_QUEUE = "example-topic-queue";
    public static final String OTHER_EXAMPLE_TOPIC_QUEUE = "other-example-topic-queue";
    public static final String THIRD_EXAMPLE_TOPIC_QUEUE = "third-example-topic-queue";
    public static final String LOG_MESSAGE = "Consume QUEUE {}, with ROUTING_KEY = {}, with VALUE = {}";

    private Logger LOG = LoggerFactory.getLogger(TopicConsumerService.class);

    private final ObjectMapper objectMapper;

    public TopicConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = EXAMPLE_TOPIC_QUEUE)
    public void exampleTopicQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            var routingKey = message.getMessageProperties().getReceivedRoutingKey();
            LOG.info(LOG_MESSAGE, EXAMPLE_TOPIC_QUEUE, routingKey, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }

    @RabbitListener(queues = OTHER_EXAMPLE_TOPIC_QUEUE)
    public void otherTopicQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            var routingKey = message.getMessageProperties().getReceivedRoutingKey();
            LOG.info(LOG_MESSAGE, OTHER_EXAMPLE_TOPIC_QUEUE, routingKey, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }

    @RabbitListener(queues = THIRD_EXAMPLE_TOPIC_QUEUE)
    public void thirdTopicQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            var routingKey = message.getMessageProperties().getReceivedRoutingKey();
            LOG.info(LOG_MESSAGE, THIRD_EXAMPLE_TOPIC_QUEUE, routingKey, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }
}
