package com.invillia.denver.listenfactoryconsumer.config;

import com.invillia.denver.listenfactoryconsumer.listener.AdminListenTopicListener;
import com.invillia.denver.listenfactoryconsumer.listener.OtherAdminListenTopicListener;
import com.invillia.denver.listenfactoryconsumer.listener.ThirdAdminListenTopicListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConsumerConfig {

    private final ConnectionFactory connectionFactory;
    private final AdminListenTopicListener adminListenTopicListener;
    private final OtherAdminListenTopicListener otherAdminListenTopicListener;
    private final ThirdAdminListenTopicListener thirdAdminListenTopicListener;

    public TopicConsumerConfig(ConnectionFactory connectionFactory,
                               AdminListenTopicListener adminListenTopicListener,
                               OtherAdminListenTopicListener otherAdminListenTopicListener,
                               ThirdAdminListenTopicListener thirdAdminListenTopicListener) {
        this.connectionFactory = connectionFactory;
        this.adminListenTopicListener = adminListenTopicListener;
        this.otherAdminListenTopicListener = otherAdminListenTopicListener;
        this.thirdAdminListenTopicListener = thirdAdminListenTopicListener;
    }

    @Bean
    public MessageListenerContainer adminTopicConfig() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QueueConstants.ADMIN_LISTEN_TOPIC_QUEUE);
        container.setMessageListener(adminListenTopicListener);
        container.start();
        return container;
    }

    @Bean
    public MessageListenerContainer otherAdminTopicConfig() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QueueConstants.OTHER_ADMIN_LISTEN_TOPIC_QUEUE);
        container.setMessageListener(otherAdminListenTopicListener);
        container.start();
        return container;
    }

    @Bean
    public MessageListenerContainer thirdAdminTopicConfig() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QueueConstants.THIRD_ADMIN_LISTEN_TOPIC_QUEUE);
        container.setMessageListener(thirdAdminListenTopicListener);
        container.start();
        return container;
    }
}
