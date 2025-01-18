package com.test.Scheduler.service;

import com.test.Scheduler.model.ChangeLog;
import com.test.Scheduler.model.JmsMessageSender;
import com.test.Scheduler.repository.ChangeLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ChangeLogService {

    private final ChangeLogRepository changeLogRepository;
    private final JmsMessageSender jmsMessageSender;

    public ChangeLogService(ChangeLogRepository changeLogRepository, JmsMessageSender jmsMessageSender) {
        this.changeLogRepository = changeLogRepository;
        this.jmsMessageSender = jmsMessageSender;
    }

    @Transactional
    public void logChange(String changeType, Long entityId, String entityClass, String changeDetails) {
        // Сохраняем запись о событии
        ChangeLog changeLog = new ChangeLog();
        changeLog.setChangeType(changeType);
        changeLog.setEntityId(entityId);
        changeLog.setEntityClass(entityClass);
        changeLog.setChangeDetails(changeDetails);
        changeLogRepository.save(changeLog);

        System.out.println("ChangeLog saved: " + changeLog);

        // Отправка сообщения в очередь
        String message = String.format("Type: %s, ID: %d, Class: %s, Details: %s",
                changeType, entityId, entityClass, changeDetails);
        jmsMessageSender.sendMessage(message);

        System.out.println("Message sent to queue: " + message);
    }
}
