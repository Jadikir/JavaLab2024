package com.test.Scheduler.repository;

import com.test.Scheduler.model.NotificationCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationCondition, Long> {
    List<NotificationCondition> findByEntityType(String entityType);
}
