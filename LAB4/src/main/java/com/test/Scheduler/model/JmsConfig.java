package com.test.Scheduler.model;
import jakarta.jms.Topic;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class JmsConfig {
    @Bean
    public Topic destinationTopic() {
        return new ActiveMQTopic("change-log-topic");
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        return connectionFactory;
    }

    @Bean
    public SingleConnectionFactory singleConnectionFactory(ActiveMQConnectionFactory connectionFactory) {
        SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory(connectionFactory);
        singleConnectionFactory.setClientId("my-client-id");
        return singleConnectionFactory;
    }


    @Bean
    public JmsTemplate jmsTemplate(SingleConnectionFactory singleConnectionFactory) {
        return new JmsTemplate(singleConnectionFactory);
    }


    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(SingleConnectionFactory singleConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(singleConnectionFactory);
        factory.setPubSubDomain(true);
        factory.setSubscriptionDurable(true);
        return factory;
    }
}
