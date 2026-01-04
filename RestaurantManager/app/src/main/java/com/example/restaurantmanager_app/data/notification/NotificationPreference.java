package com.example.restaurantmanager_app.data.notification;

public class NotificationPreference {
    private int preferenceId;
    private int userId;
    private int new_reservation;
    private int reservation_cancelled;
    private int reservation_changed;

    public NotificationPreference(int preferenceId, int userId, int new_reservation_notification, int reservation_cancelled_notification, int reservation_changed_notification) {
        this.preferenceId = preferenceId;
        this.userId = userId;
        this.new_reservation = new_reservation_notification;
        this.reservation_cancelled = reservation_cancelled_notification;
        this.reservation_changed = reservation_changed_notification;
    }

    public int getPreferenceId() { return preferenceId; }
    public int getUserId() { return userId; }
    public int getNew_reservation() { return new_reservation; }
    public int getReservation_cancelled() { return reservation_cancelled; }
    public int getReservation_changed() { return reservation_changed; }

}
