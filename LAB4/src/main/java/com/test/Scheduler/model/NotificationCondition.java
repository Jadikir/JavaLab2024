package com.test.Scheduler.model;



import jakarta.persistence.*;


@Entity
@Table(name = "notification_conditions")
public class NotificationCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String entityType;  // Тип сущности (например, "Room", "Booking")

    @Column(nullable = true)
    private String entityAttribute;  // Атрибут сущности (например, "capacity", "startTime")

    @Column(nullable = true)
    private String condition;  // Условие для атрибута (например, "capacity > 10")

    @Column(nullable = false)
    private String email;  // Email для уведомлений

    public NotificationCondition() {
    }

    public NotificationCondition(String entityType, String entityAttribute, String condition, String email) {
        this.entityType = entityType;
        this.entityAttribute = entityAttribute;
        this.condition = condition;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityAttribute() {
        return entityAttribute;
    }

    public void setEntityAttribute(String entityAttribute) {
        this.entityAttribute = entityAttribute;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
