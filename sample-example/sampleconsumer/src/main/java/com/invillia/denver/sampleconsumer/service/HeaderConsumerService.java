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
public class HeaderConsumerService {

    public static final String SAMPLE_HEADER_QUEUE = "sample-header-queue";
    public static final String OTHER_SAMPLE_HEADER_QUEUE = "other-sample-header-queue";
    public static final String LOG_MESSAGE = "Consume QUEUE {}, with HEADERS = {}, with VALUE = {}";

    private Logger LOG = LoggerFactory.getLogger(HeaderConsumerService.class);

    private final ObjectMapper objectMapper;

    public HeaderConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = SAMPLE_HEADER_QUEUE)
    public void sampleHeaderQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), ProductConsumer.class);
            var args = message.getMessageProperties().getHeaders();
            LOG.info(LOG_MESSAGE, SAMPLE_HEADER_QUEUE, args, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }

    @RabbitListener(queues = OTHER_SAMPLE_HEADER_QUEUE)
    public void otherHeaderQueueConsumer(Message message) throws IOException {
        try {
            var product = objectMapper.readValue(message.getBody(), ProductConsumer.class);
            var args = message.getMessageProperties().getHeaders();
            LOG.info(LOG_MESSAGE, OTHER_SAMPLE_HEADER_QUEUE, args, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
            throw e;
        }
    }
}
