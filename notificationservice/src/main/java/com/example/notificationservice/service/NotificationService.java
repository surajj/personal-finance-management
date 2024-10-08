package com.example.notificationservice.service;

import com.example.notificationservice.entity.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    // Method to create and save a new notification
    public Notification sendNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    // Method to retrieve all notifications (optional)
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

}
