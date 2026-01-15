package com.example.restaurantmanager_app.user_management;

import android.content.Context;
import android.util.Log;

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
        UserService.getAllUsers(context, studentId, new UserService.UsersResponseCallback() {

            // If authentication is successful, create a session and return the user type
            // Else return an error message
            @Override
            public void onSuccess(List<User> users) {
                for (User user : users) {
                    Log.d("AuthManager", "Checking email: " + user.getEmail());
                    Log.d("AuthManager", "Checking password: " + user.getPassword());

                    if (user.getEmail().equals(inputEmail)
                            &&
                        user.getPassword().equals(inputPassword)) {

                        sessionManager.createSession(
                                user.getUsername(),
                                user.getEmail(),
                                user.getPassword(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getContact(),
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

    public void register(
            String studentId,
            User user,
            AuthCallback callback
    ) {
        UserService.createUser(context, studentId, user, new UserService.UserResponseCallback() {

            @Override
            public void onSuccess(User users) {
                sessionManager.createSession(
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getContact(),
                        user.getUsertype()
                );

                callback.onSuccess(user.getUsertype());
            }

            @Override
            public void onError(String message) {
                callback.onFailure(message);
            }
        });

    }

    public void updateUserDetails(
            String studentId,
            String username,
            User user,
            AuthCallback callback
    ) {
        UserService.updateUser(context, studentId, username, user, new UserService.UserResponseCallback() {
            @Override
            public void onSuccess(User users) {
                sessionManager.setDetails(
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getContact(),
                        user.getUsertype()
                );

                callback.onSuccess(user.getUsertype());
            }

            @Override
            public void onError(String message) {
                callback.onFailure(message);
            }
        });
    }

    public void deleteAccount(
            String studentId,
            String username,
            AuthCallback callback
    ) {
        UserService.deleteUser(context, studentId, username, new UserService.UserResponseCallback() {
            @Override
            public void onSuccess(User users) {
                callback.onSuccess(null);
            }

            @Override
            public void onError(String message) {
                callback.onFailure(message);
            }
        });
    }

    public void logout() {
        sessionManager.logout();
    }
}

