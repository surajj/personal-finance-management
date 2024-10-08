package com.example.notificationservice.controller;

import com.example.notificationservice.entity.Notification;
import com.example.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification) {
        // Process the notification
        notificationService.sendNotification(notification);

        return ResponseEntity.ok("Notification sent for category: " + notification.getCategory());
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotification(){

        return ResponseEntity.ok(notificationService.getAllNotifications());
    }
}
