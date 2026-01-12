package com.example.restaurantmanager_app.user_management;

import android.content.Context;

import com.example.restaurantmanager_app.user_management.User;
import com.example.restaurantmanager_app.user_management.UserService;
//import com.example.restaurantmanager_app.user_management.SessionManager;

import java.util.List;

public class AuthManager {

    private final Context context;
    private final SessionManager sessionManager;

    public AuthManager(Context context) {
        this.context = context;
        this.sessionManager = new SessionManager(context);
    }

    public void login(
            String studentId,
            String inputEmail,
            String inputPassword,
            AuthCallback callback
    ) {
        UserService.getAllUsers(context, studentId, new UserService.UserResponseCallback() {

            // If authentication is successful, create a session and return the user type
            // Else return an error message
            @Override
            public void onSuccess(List<User> users) {
                for (User user : users) {
                    if (user.getEmail().equals(inputEmail)
                            &&
                        user.getPassword().equals(inputPassword)) {

                        sessionManager.createSession(
                                user.getUsername(),
                                user.getUsertype()
                        );

                        callback.onSuccess(user.getUsertype());
                        return;
                    }
                }
                callback.onFailure("Invalid username or password");
            }

            @Override
            public void onError(String message) {
                callback.onFailure(message);
            }
        });
    }
}

