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
public class OtherSampleManualHeaderListener implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(OtherSampleManualHeaderListener.class);
    private final ObjectMapper objectMapper;

    public OtherSampleManualHeaderListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onMessage(Message message) {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            var routingKey = message.getMessageProperties().getReceivedRoutingKey();
            logger.info(QueueConstants.LOG_MESSAGE_ROUTING_KEY,
                    QueueConstants.OTHER_SAMPLE_MANUAL_HEADER_QUEUE, routingKey, product);
        } catch (IOException e) {
            logger.error("Error to consume message", e);
        }

    }
}
