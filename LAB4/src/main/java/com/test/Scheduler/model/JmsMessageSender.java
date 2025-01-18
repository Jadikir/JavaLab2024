package com.test.Scheduler.model;


import jakarta.jms.Topic;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;



@Component

public class JmsMessageSender {

    private final JmsTemplate jmsTemplate;
    private final Topic destinationTopic;

    public JmsMessageSender(JmsTemplate jmsTemplate, Topic destinationTopic) {
        this.jmsTemplate = jmsTemplate;
        this.destinationTopic = destinationTopic;
    }

    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(destinationTopic, message);
    }
}
