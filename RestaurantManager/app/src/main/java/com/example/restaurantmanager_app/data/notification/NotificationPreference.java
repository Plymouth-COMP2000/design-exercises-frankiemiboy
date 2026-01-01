package com.example.restaurantmanager_app.data.notification;

public class NotificationPreference {
    private int preferenceId;
    private int userId;
    private boolean new_reservation_notification;
    private boolean reservation_cancelled_notification;
    private boolean reservation_changed_notification;

    public NotificationPreference(int preferenceId, int userId, boolean new_reservation_notification, boolean reservation_cancelled_notification, boolean reservation_changed_notification) {
        this.preferenceId = preferenceId;
        this.userId = userId;
        this.new_reservation_notification = new_reservation_notification;
        this.reservation_cancelled_notification = reservation_cancelled_notification;
        this.reservation_changed_notification = reservation_changed_notification;
    }

    public int getPreferenceId() { return preferenceId; }
    public int getUserId() { return userId; }
    public boolean getNew_reservation_notification() { return new_reservation_notification; }
    public boolean getReservation_cancelled_notification() { return reservation_cancelled_notification; }
    public boolean getReservation_changed_notification() { return reservation_changed_notification; }

}
