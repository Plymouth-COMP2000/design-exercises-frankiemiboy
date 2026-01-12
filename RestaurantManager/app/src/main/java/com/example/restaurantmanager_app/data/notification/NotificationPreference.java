package com.example.restaurantmanager_app.data.notification;

public class NotificationPreference {
    private int preferenceId;
    private String username;
    private String notificationType;
    private int enabled;

    public NotificationPreference(int preferenceId, String username, String reservationType, int enabled) {
        this.preferenceId = preferenceId;
        this.username = username;
        this.notificationType = reservationType;
        this.enabled = enabled;
    }

    // Getter Methods
    public int getPreferenceId() { return preferenceId; }
    public String getUsername() { return username; }
    public String getNotificationType() { return notificationType; }
    public int getEnabled() { return enabled; }

    // Setter Methods
    public void setPreferenceId(int preferenceId) { this.preferenceId = preferenceId; }
    public void setUsername(String username) { this.username = username; }
    public void setNotificationType(String reservationType) { this.notificationType = reservationType; }
    public void setEnabled(int enabled) { this.enabled = enabled; }
}
