package com.test.Scheduler.service;

import com.test.Scheduler.model.ChangeLog;
import com.test.Scheduler.repository.ChangeLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeLogService {

    private final ChangeLogRepository changeLogRepository;

    public ChangeLogService(ChangeLogRepository changeLogRepository) {
        this.changeLogRepository = changeLogRepository;
    }

    @Transactional
    public void logChange(String changeType, Long entityId, String entityClass, String changeDetails) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setChangeType(changeType);
        changeLog.setEntityId(entityId);
        changeLog.setEntityClass(entityClass);
        changeLog.setChangeDetails(changeDetails);
        changeLogRepository.save(changeLog);
    }
}
