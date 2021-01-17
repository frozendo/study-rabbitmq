package com.invillia.denver.listenfactoryconsumer.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invillia.denver.listenfactoryconsumer.config.QueueConstants;
import com.invillia.denver.listenfactoryconsumer.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class AdminListenDirectListener implements MessageListener {

    private Logger LOG = LoggerFactory.getLogger(AdminListenDirectListener.class);

    private final ObjectMapper objectMapper;

    public AdminListenDirectListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onMessage(Message message) {
        try {
            var product = objectMapper.readValue(message.getBody(), Product.class);
            var routingKey = message.getMessageProperties().getReceivedRoutingKey();
            LOG.info(QueueConstants.LOG_MESSAGE_ROUTING_KEY, QueueConstants.ADMIN_LISTEN_DIRECT_QUEUE, routingKey, product);
        } catch (IOException e) {
            LOG.error("Error to consume message", e);
        }

    }
}