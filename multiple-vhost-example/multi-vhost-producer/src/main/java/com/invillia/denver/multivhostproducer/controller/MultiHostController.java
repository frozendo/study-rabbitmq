package com.invillia.denver.multivhostproducer.controller;

import com.invillia.denver.multivhostproducer.config.FanoutConfig;
import com.invillia.denver.multivhostproducer.config.TopicConfig;
import com.invillia.denver.multivhostproducer.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
public class MultiHostController {

    private Logger LOG = LoggerFactory.getLogger(MultiHostController.class);

    private final RabbitTemplate primaryRabbitTemplate;
    private final RabbitTemplate topicRabbitTemplate;

    public MultiHostController(RabbitTemplate primaryRabbitTemplate,
                               @Qualifier("topicRabbitTemplate") RabbitTemplate topicRabbitTemplate) {
        this.primaryRabbitTemplate = primaryRabbitTemplate;
        this.topicRabbitTemplate = topicRabbitTemplate;
    }

    @PostMapping
    public void executeExample(@RequestBody Product product) {
        LOG.info("sending message for = multi-vhost-fanout-exchange");
        primaryRabbitTemplate.convertAndSend(FanoutConfig.MULTI_VHOST_FANOUT_EXCHANGE, "", product);
    }

    @PostMapping("/{routingKey}")
    public void executeExample(@PathVariable("routingKey") String routingKey,
                                              @RequestBody Product product) {
        LOG.info("sending message for = multi-vhost-topic-exchange with routing key = {}", routingKey);
        topicRabbitTemplate.convertAndSend(TopicConfig.MULTI_VHOST_TOPIC_EXCHANGE, routingKey, product);
    }
}
