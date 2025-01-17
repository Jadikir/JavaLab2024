package com.test.Scheduler.model;

import jakarta.jms.Queue;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;



@Component
public class JmsMessageSender {

    private final JmsTemplate jmsTemplate;
    private final Queue destinationQueue;

    public JmsMessageSender(JmsTemplate jmsTemplate, Queue destinationQueue) {
        this.jmsTemplate = jmsTemplate;
        this.destinationQueue = destinationQueue;
    }

    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(destinationQueue, message);
    }
}
