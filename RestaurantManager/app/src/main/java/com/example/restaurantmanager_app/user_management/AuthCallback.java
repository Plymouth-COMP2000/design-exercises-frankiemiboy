package com.example.restaurantmanager_app.user_management;

// Made an interface so that we do not have any UI logic in here
// This is needed because the API does not respond instantly
public interface AuthCallback {
    void onSuccess(String userType);
    void onFailure(String message);

}
