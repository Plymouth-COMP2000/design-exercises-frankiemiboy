package com.example.restaurantmanager_app.user_management;

import android.content.Context;
import android.content.SharedPreferences;

// The class that will manage the session of a user
public class SessionManager {

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
    }

    public void createSession(String username, String role) {
        prefs.edit()
                .putBoolean("loggedIn", true)
                .putString("username", username)
                .putString("role", role)
                .apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean("loggedIn", false);
    }

    public String getUsername() {
        return prefs.getString("username", "");
    }

    public String getRole() {
        return prefs.getString("role", "");
    }

    public void logout() {
        prefs.edit().clear().apply();
    }
}
