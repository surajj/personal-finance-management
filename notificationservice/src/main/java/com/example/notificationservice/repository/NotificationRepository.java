package com.example.notificationservice.repository;

import com.example.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Additional query methods can be added here if needed
}