package com.invillia.denver.sampleconsumer.config;

import com.invillia.denver.sampleconsumer.listener.OtherSampleManualDirectListener;
import com.invillia.denver.sampleconsumer.listener.SampleManualDirectListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConsumerConfig {

    private final ConnectionFactory connectionFactory;
    private final SampleManualDirectListener sampleManualDirectListener;
    private final OtherSampleManualDirectListener otherSampleManualDirectListener;

    public DirectConsumerConfig(ConnectionFactory connectionFactory,
                                SampleManualDirectListener sampleManualDirectListener,
                                OtherSampleManualDirectListener otherSampleManualDirectListener) {
        this.connectionFactory = connectionFactory;
        this.sampleManualDirectListener = sampleManualDirectListener;
        this.otherSampleManualDirectListener = otherSampleManualDirectListener;
    }

    @Bean
    public MessageListenerContainer sampleManualDirectConfig() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QueueConstants.SAMPLE_MANUAL_DIRECT_QUEUE);
        container.setMessageListener(sampleManualDirectListener);
        container.start();
        return container;
    }

    @Bean
    public MessageListenerContainer otherManualDirectConfig() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QueueConstants.OTHER_SAMPLE_MANUAL_DIRECT_QUEUE);
        container.setMessageListener(otherSampleManualDirectListener);
        container.start();
        return container;
    }
}
