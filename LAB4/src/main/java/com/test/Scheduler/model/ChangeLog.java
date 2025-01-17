package com.test.Scheduler.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "change_log")
public class ChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "change_type", nullable = false)
    private String changeType; // Тип изменения (INSERT, UPDATE, DELETE)

    @Column(name = "entity_id", nullable = false)
    private Long entityId; // Идентификатор сущности

    @Column(name = "entity_class", nullable = false)
    private String entityClass; // Класс сущности

    @Column(name = "change_details", columnDefinition = "TEXT")
    private String changeDetails; // Подробности изменения

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now(); // Временная метка

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    public String getChangeDetails() {
        return changeDetails;
    }

    public void setChangeDetails(String changeDetails) {
        this.changeDetails = changeDetails;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
