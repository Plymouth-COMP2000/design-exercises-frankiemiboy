package com.example.restaurantmanager_app.data.notification;

public class NotificationPreference {
    private int preferenceId;
    private int userId;
    private String notificationType;
    private int enabled;

    public NotificationPreference(int preferenceId, int userId, String reservationType, int enabled) {
        this.preferenceId = preferenceId;
        this.userId = userId;
        this.notificationType = reservationType;
        this.enabled = enabled;
    }

    // Getter Methods
    public int getPreferenceId() { return preferenceId; }
    public int getUserId() { return userId; }
    public String getNotificationType() { return notificationType; }
    public int getEnabled() { return enabled; }

    // Setter Methods
    public void setPreferenceId(int preferenceId) { this.preferenceId = preferenceId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setNotificationType(String reservationType) { this.notificationType = reservationType; }
    public void setEnabled(int enabled) { this.enabled = enabled; }
}
