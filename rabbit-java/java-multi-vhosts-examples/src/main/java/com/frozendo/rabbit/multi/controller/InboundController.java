package com.frozendo.rabbit.multi.controller;

import com.frozendo.rabbit.multi.domain.MessageDTO;
import com.frozendo.rabbit.multi.producer.ProductProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
public class InboundController {

    private Logger logger = LoggerFactory.getLogger(InboundController.class);

    private final ProductProducerService productProducerService;

    public InboundController(ProductProducerService productProducerService) {
        this.productProducerService = productProducerService;
    }

    @PostMapping
    public void executeExample(@RequestBody MessageDTO dto) {
        productProducerService.sendMessage(dto);
    }

}
