package com.example.restaurantmanager_app.data.notification;


// The structure of a notification object
public class Notification {
    private int notificationId;
    private String username;
    private int reservationId;
    private String Title;
    private String message;
    private int is_read;
    private String created_at;

    public Notification (int notificationId, String username, int reservationId, String Title, String message, int is_read, String created_at) {
        this.notificationId = notificationId;
        this.username = username;
        this.reservationId = reservationId;
        this.Title = Title;
        this.message = message;
        this.is_read = is_read;
        this.created_at = created_at;
    }

    // The getter functions
    public int getNotificationId() { return notificationId; }
    public String getUsername() { return username; }
    public int getReservationId() { return reservationId; }
    public String getTitle() { return Title; }
    public String getMessage() { return message; }
    public int getIsRead() { return is_read; }
    public String getCreatedAt() { return created_at; }

}
