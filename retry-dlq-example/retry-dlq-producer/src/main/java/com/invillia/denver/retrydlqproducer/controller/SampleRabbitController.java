package com.invillia.denver.retrydlqproducer.controller;

import com.invillia.denver.retrydlqproducer.config.QueueConstants;
import com.invillia.denver.retrydlqproducer.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
public class SampleRabbitController {

    private final Logger logger = LoggerFactory.getLogger(SampleRabbitController.class);
    private final RabbitTemplate rabbitTemplate;

    public SampleRabbitController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/{routingKey}")
    public void executeExample(@PathVariable("routingKey") String routingKey,
                               @RequestBody Product product) {

        logger.info("sending message for = {} with routing key = {}", QueueConstants.RETRY_DIRECT_EXCHANGE, routingKey);
        rabbitTemplate.convertAndSend(QueueConstants.RETRY_DIRECT_EXCHANGE, routingKey, product);

    }
}
