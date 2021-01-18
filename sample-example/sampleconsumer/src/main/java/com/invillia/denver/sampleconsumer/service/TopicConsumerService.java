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
public class TopicConsumerService {

    private Logger LOG = LoggerFactory.getLogger(TopicConsumerService.class);

    private final ObjectMapper objectMapper;

    public TopicConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = QueueConstants.SAMPLE_BEAN_TOPIC_QUEUE)
    public void sampleTopicQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            var routingKey = message.getMessageProperties().getReceivedRoutingKey();
            LOG.info(QueueConstants.LOG_MESSAGE_ROUTING_KEY, QueueConstants.SAMPLE_BEAN_TOPIC_QUEUE, routingKey, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }

    @RabbitListener(queues = QueueConstants.OTHER_SAMPLE_BEAN_TOPIC_QUEUE)
    public void otherTopicQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            var routingKey = message.getMessageProperties().getReceivedRoutingKey();
            LOG.info(QueueConstants.LOG_MESSAGE_ROUTING_KEY, QueueConstants.OTHER_SAMPLE_BEAN_TOPIC_QUEUE, routingKey, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }

    @RabbitListener(queues = QueueConstants.THIRD_SAMPLE_BEAN_TOPIC_QUEUE)
    public void thirdTopicQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            var routingKey = message.getMessageProperties().getReceivedRoutingKey();
            LOG.info(QueueConstants.LOG_MESSAGE_ROUTING_KEY, QueueConstants.THIRD_SAMPLE_BEAN_TOPIC_QUEUE, routingKey, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }
}
