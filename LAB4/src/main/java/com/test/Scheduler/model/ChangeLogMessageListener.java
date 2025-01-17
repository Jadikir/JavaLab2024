package com.test.Scheduler.model;

import com.test.Scheduler.repository.ChangeLogRepository;
import com.test.Scheduler.repository.NotificationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ChangeLogMessageListener {

    private final ChangeLogRepository changeLogRepository;
    private final NotificationRepository notificationRepository;
    private final JavaMailSender emailSender;

    public ChangeLogMessageListener(ChangeLogRepository changeLogRepository,
                                    NotificationRepository notificationRepository,
                                    JavaMailSender emailSender) {
        this.changeLogRepository = changeLogRepository;
        this.notificationRepository = notificationRepository;
        this.emailSender = emailSender;
    }

    @JmsListener(destination = "change-log-queue")
    public void processMessage(String message) {
        // Разбор сообщения
        Pattern pattern = Pattern.compile("Type: (.*?), ID: (\\d+), Class: (.*?), Details: (.*)");
        Matcher matcher = pattern.matcher(message);

        if (matcher.matches()) {
            String changeType = matcher.group(1); // Извлекаем changeType
            Long entityId = Long.parseLong(matcher.group(2)); // Извлекаем entityId
            String entityClass = matcher.group(3); // Извлекаем entityClass
            String changeDetails = matcher.group(4); // Извлекаем changeDetails

            // Выводим данные для проверки
            System.out.println("Change Type: " + changeType);
            System.out.println("Entity ID: " + entityId);
            System.out.println("Entity Class: " + entityClass);
            System.out.println("Change Details: " + changeDetails);

            // Проверка условий и отправка email-уведомлений
            List<NotificationCondition> conditions = notificationRepository.findByEntityType(entityClass);
            System.out.println("Conditions found: " + conditions);

            for (NotificationCondition condition : conditions) {
                System.out.println("Checking condition for attribute: " + condition.getEntityAttribute() + " with condition: " + condition.getCondition());

                if (isConditionMet(condition, changeDetails)) {
                    System.out.println("Condition met: " + condition.getCondition());
                    sendEmail(condition.getEmail(), changeType, entityId, entityClass, changeDetails);
                } else {
                    System.out.println("Condition not met: " + condition.getCondition());
                }
            }
        }
        else {System.out.println("Message format is incorrect.");}

    }

    private boolean isConditionMet(NotificationCondition condition, String changeDetails) {
        if (condition.getEntityAttribute() == null || condition.getCondition() == null) {
            System.out.println("Condition or attribute is null, returning false");
            return false; // Если атрибут или условие пустое, то условие не выполнено
        }

        String attribute = condition.getEntityAttribute().toLowerCase();
        String conditionStr = condition.getCondition().toLowerCase();


        // Разделяем операцию и значение в условии
        String operation = conditionStr.substring(0, 1); // Операция (например, ">" или "<")
        int conditionValue = Integer.parseInt(conditionStr.substring(1));

        // Извлекаем значение атрибута из строки changeDetails
        String valuePattern = attribute + "\\s*[:]?\\s*'?(\\d+)'?"; // Паттерн для числового значения атрибута
        Pattern pattern = Pattern.compile(valuePattern);
        Matcher matcher = pattern.matcher(changeDetails.toLowerCase());

        boolean foundValue = false;
        int attrValue = 0;

        while (matcher.find()) {

            String value = matcher.group(1);
            attrValue = Integer.parseInt(value);
            foundValue = true;
        }

        if (!foundValue) {
            System.out.println("Attribute value not found in changeDetails");
            return false; // Значение атрибута не найдено
        }


        switch (operation) {
            case ">":
                return attrValue > conditionValue;
            case "<":
                return attrValue < conditionValue;
            case "=":
                return attrValue == conditionValue;
            case "!=":
                return attrValue != conditionValue;
            case ">=":
                return attrValue >= conditionValue;
            case "<=":
                return attrValue <= conditionValue;
            default:
                System.out.println("Unsupported operation: " + operation);
                return false;
        }
    }

    private void sendEmail(String recipient, String changeType, Long entityId, String entityClass, String changeDetails) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipient);
            helper.setSubject("Change Log Notification: " + changeType);
            helper.setText("Change Type: " + changeType + "\n" +
                    "Entity ID: " + entityId + "\n" +
                    "Entity Class: " + entityClass + "\n" +
                    "Change Details: " + changeDetails);

            emailSender.send(message);

            System.out.println("Email sent to: " + recipient);

        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
