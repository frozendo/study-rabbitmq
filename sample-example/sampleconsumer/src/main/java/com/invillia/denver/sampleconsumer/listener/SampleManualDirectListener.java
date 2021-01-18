package com.invillia.denver.sampleconsumer.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invillia.denver.sampleconsumer.config.QueueConstants;
import com.invillia.denver.sampleconsumer.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SampleManualDirectListener implements MessageListener {

    private Logger LOG = LoggerFactory.getLogger(SampleManualDirectListener.class);

    private final ObjectMapper objectMapper;

    public SampleManualDirectListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onMessage(Message message) {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            var routingKey = message.getMessageProperties().getReceivedRoutingKey();
            LOG.info(QueueConstants.LOG_MESSAGE_ROUTING_KEY, QueueConstants.SAMPLE_MANUAL_DIRECT_QUEUE, routingKey, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
        }

    }
}
