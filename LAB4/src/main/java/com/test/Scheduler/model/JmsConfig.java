package com.test.Scheduler.model;
import jakarta.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
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
    public Queue destinationQueue() {
        return new ActiveMQQueue("change-log-queue");
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616"); // Пример брокера
        return connectionFactory;
    }

    @Bean
    public SingleConnectionFactory singleConnectionFactory(ActiveMQConnectionFactory connectionFactory) {
        return new SingleConnectionFactory(connectionFactory);
    }

    @Bean
    public JmsTemplate jmsTemplate(SingleConnectionFactory singleConnectionFactory) {
        return new JmsTemplate(singleConnectionFactory);
    }

    // Настройка фабрики для слушателей JMS
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(SingleConnectionFactory singleConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(singleConnectionFactory);
        return factory;
    }
}
