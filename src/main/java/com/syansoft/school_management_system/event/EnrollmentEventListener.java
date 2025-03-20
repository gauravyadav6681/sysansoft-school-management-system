package com.syansoft.school_management_system.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentEventListener {
    @KafkaListener(topics = "enrollment_topic", groupId = "school-group")
    public void listenEnrollmentEvents(String message) {
        System.out.println("Received Enrollment Event: " + message);
    }
}

