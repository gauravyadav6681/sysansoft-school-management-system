package com.syansoft.school_management_system.scheduler;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.syansoft.school_management_system.entity.User;
import com.syansoft.school_management_system.repository.UserRepository;

@Component
public class ScheduledTask {

    @Autowired
    private UserRepository userRepository; 

    @Scheduled(cron = "0 */10 * * * ?")
    public void fetchAndPrintUsers() {
        List<User> users = userRepository.findAll(); 
        System.out.println("🔄 Scheduled Task Running: " + java.time.LocalTime.now());

        if (users.isEmpty()) {
            System.out.println("⚠️ No users found in the database.");
        } else {
            System.out.println("📜 List of Users:");
            for (User user : users) {
                System.out.println("🆔 ID: " + user.getId() + " | 👤 Username: " + user.getUsername() + " | 📧 Email: " + user.getEmail() + " | 🔖 Role: " + user.getRole());
            }
        }
    }
    }