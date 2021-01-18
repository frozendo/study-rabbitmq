package com.invillia.denver.sampleconsumer.config;

import com.invillia.denver.sampleconsumer.listener.OtherSampleManualHeaderListener;
import com.invillia.denver.sampleconsumer.listener.SampleManualHeaderListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeaderConsumerConfig {

    private final ConnectionFactory connectionFactory;
    private final SampleManualHeaderListener sampleManualHeaderListener;
    private final OtherSampleManualHeaderListener otherSampleManualHeaderListener;

    public HeaderConsumerConfig(ConnectionFactory connectionFactory,
                                SampleManualHeaderListener sampleManualHeaderListener,
                                OtherSampleManualHeaderListener otherSampleManualHeaderListener) {
        this.connectionFactory = connectionFactory;
        this.sampleManualHeaderListener = sampleManualHeaderListener;
        this.otherSampleManualHeaderListener = otherSampleManualHeaderListener;
    }

    @Bean
    public MessageListenerContainer sampleManualHeaderConfig() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QueueConstants.SAMPLE_MANUAL_HEADER_QUEUE);
        container.setMessageListener(sampleManualHeaderListener);
        container.start();
        return container;
    }

    @Bean
    public MessageListenerContainer otherSampleManualHeaderConfig() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QueueConstants.OTHER_SAMPLE_MANUAL_HEADER_QUEUE);
        container.setMessageListener(otherSampleManualHeaderListener);
        container.start();
        return container;
    }
}
