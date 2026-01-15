package com.example.restaurantmanager_app.user_management;

import android.content.Context;
import android.content.SharedPreferences;

// The class that will manage the session of a user
public class SessionManager {

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
    }

    public void createSession(String username, String email, String password, String firstName, String lastName, String phoneNumber, String role) {
        prefs.edit()
                .putBoolean("loggedIn", true)
                .putString("username", username)
                .putString("email", email)
                .putString("password", password)
                .putString("firstname", firstName)
                .putString("lastname", lastName)
                .putString("phoneNumber", phoneNumber)
                .putString("role", role)
                .apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean("loggedIn", false);
    }

    public String getFirstName() {
        return prefs.getString("firstname", "");
    }

    public String getLastName() {
        return prefs.getString("lastname", "");
    }

    public String getEmail() {
        return prefs.getString("email", "");
    }

    public String getUsername() {
        return prefs.getString("username", "");
    }

    public String getPassword() {
        return prefs.getString("password", "");
    }

    public String getPhoneNumber() {
        return prefs.getString("phoneNumber", "");
    }

    public String getRole() {
        return prefs.getString("role", "");
    }

    // Setter Methods
    public void setFirstName(String firstName) {
        prefs.edit().putString("firstname", firstName).apply();
    }

    public void setLastName(String lastName) {
        prefs.edit().putString("lastname", lastName).apply();
    }

    public void setEmail(String email) {
        prefs.edit().putString("email", email).apply();
    }

    public void setUsername(String username) {
        prefs.edit().putString("username", username).apply();
    }

    public void setPassword(String password) {
        prefs.edit().putString("password", password).apply();
    }

    public void setPhoneNumber(String phoneNumber) {
        prefs.edit().putString("phoneNumber", phoneNumber).apply();
    }

    public void setRole(String role) {
        prefs.edit().putString("role", role).apply();
    }

    public void setDetails(
            String username, String email, String password,
            String firstName, String lastName, String phoneNumber, String role
    ) {
        prefs.edit()
                .putString("username", username)
                .putString("email", email)
                .putString("password", password)
                .putString("firstname", firstName)
                .putString("lastname", lastName)
                .putString("phoneNumber", phoneNumber)
                .putString("role", role)
                .apply();
    }

    public void logout() {
        prefs.edit().clear().apply();
    }

}
