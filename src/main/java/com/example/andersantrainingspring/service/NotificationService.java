package com.example.andersantrainingspring.service;

public class NotificationService {

    private static NotificationService instance;

    private NotificationService() {
        // private constructor so no one else can instantiate
    }

    public static synchronized NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    public void sendNotification(String message) {
        System.out.println("📢 Notification: " + message);
    }
}
