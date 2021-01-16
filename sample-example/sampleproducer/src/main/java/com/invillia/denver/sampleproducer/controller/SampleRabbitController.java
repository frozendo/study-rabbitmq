package com.invillia.denver.sampleproducer.controller;

import com.invillia.denver.sampleproducer.config.FanoutConfig;
import com.invillia.denver.sampleproducer.config.HeaderConfig;
import com.invillia.denver.sampleproducer.model.Product;
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

    private Logger LOG = LoggerFactory.getLogger(SampleRabbitController.class);

    private final RabbitTemplate rabbitTemplate;

    public SampleRabbitController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/{exchangeName}/{routingKey}")
    public void executeExample(@PathVariable("exchangeName") String exchangeName,
                               @PathVariable("routingKey") String routingKey,
                               @RequestBody Product product) {

        LOG.info("sending message for = {} with routing key = {}", exchangeName, routingKey);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, product);

    }

    @PostMapping("/header/{test}/{type}")
    public void executeHeaderExample(@PathVariable("test") String test,
                                     @PathVariable("type") String type,
                                     @RequestBody Product product) {

        LOG.info("sending message for = header-example-exchange, with test = {}, with type = {}", test, type);
        rabbitTemplate.convertAndSend(HeaderConfig.HEADER_EXAMPLE_EXCHANGE, "", product, m -> {
            m.getMessageProperties().getHeaders().put(HeaderConfig.TEST_HEADER_KEY, test);
            m.getMessageProperties().getHeaders().put(HeaderConfig.TYPE_HEADER_KEY, type);
            return m;
        });

    }

}
