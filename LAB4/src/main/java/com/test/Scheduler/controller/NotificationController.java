package com.test.Scheduler.controller;
import com.test.Scheduler.model.NotificationCondition;
import com.test.Scheduler.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification-conditions")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationController(NotificationRepository notificationConditionRepository) {
        this.notificationRepository = notificationConditionRepository;
    }


    @GetMapping
    public List<NotificationCondition> getAllConditions() {
        return notificationRepository.findAll();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationCondition createCondition(@RequestBody NotificationCondition condition) {
        return notificationRepository.save(condition);
    }


    @PutMapping("/{id}")
    public NotificationCondition updateCondition(@PathVariable Long id, @RequestBody NotificationCondition condition) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Condition not found with id " + id);
        }

        condition.setId(id);
        return notificationRepository.save(condition);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCondition(@PathVariable Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Condition not found with id " + id);
        }
        notificationRepository.deleteById(id);
    }
}
