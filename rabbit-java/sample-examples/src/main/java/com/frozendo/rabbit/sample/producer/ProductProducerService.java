package com.frozendo.rabbit.sample.producer;

import com.frozendo.rabbit.sample.domain.ExchangeTypeEnum;
import com.frozendo.rabbit.sample.domain.MessageDTO;
import com.frozendo.rabbit.sample.service.DirectExchangeService;
import com.frozendo.rabbit.sample.service.ExchangeService;
import com.frozendo.rabbit.sample.service.FanoutExchangeService;
import com.frozendo.rabbit.sample.service.HeaderExchangeService;
import com.frozendo.rabbit.sample.service.TopicExchangeService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ProductProducerService {

    private final ApplicationContext applicationContext;

    public ProductProducerService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void sendMessage(MessageDTO dto) {
        var service = getExchangeService(dto.getExchangeType());
        service.sendMessage(dto.extractProduct());
    }

    private ExchangeService getExchangeService(ExchangeTypeEnum exchangeType) {
        if (ExchangeTypeEnum.DIRECT.equals(exchangeType)) {
            return applicationContext.getBean(DirectExchangeService.class);
        } else if (ExchangeTypeEnum.TOPIC.equals(exchangeType)) {
            return applicationContext.getBean(TopicExchangeService.class);
        } else if (ExchangeTypeEnum.FANOUT.equals(exchangeType)) {
            return applicationContext.getBean(FanoutExchangeService.class);
        } else if (ExchangeTypeEnum.HEADER.equals(exchangeType)) {
            return applicationContext.getBean(HeaderExchangeService.class);
        }
        return applicationContext.getBean(DirectExchangeService.class);
    }

}
