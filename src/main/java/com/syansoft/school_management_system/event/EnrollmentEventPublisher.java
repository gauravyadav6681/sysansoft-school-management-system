package com.syansoft.school_management_system.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentEventPublisher {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    public void publishUserRegistrationEvent(String userId, String username, String role) {
        String message = "New " + role + " registered: " + username + " (ID: " + userId + ")";
        kafkaTemplate.send("user_registration_topic", message);
        System.out.println("✅ Kafka Event Sent: " + message);
    }

}



