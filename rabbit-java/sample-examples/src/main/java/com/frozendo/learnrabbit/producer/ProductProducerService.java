package com.frozendo.learnrabbit.producer;

import com.frozendo.learnrabbit.domain.ExchangeTypeEnum;
import com.frozendo.learnrabbit.domain.MessageDTO;
import com.frozendo.learnrabbit.service.DirectExchangeService;
import com.frozendo.learnrabbit.service.ExchangeService;
import com.frozendo.learnrabbit.service.TopicExchangeService;
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
        }
        return applicationContext.getBean(DirectExchangeService.class);
    }

}
