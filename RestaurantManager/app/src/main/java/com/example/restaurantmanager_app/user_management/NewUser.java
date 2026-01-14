package com.example.restaurantmanager_app.user_management;

import android.content.Context;

public class NewUser {

    private final Context context;
    private final SessionManager sessionManager;

    public NewUser(Context context) {
        this.context = context;
        this.sessionManager = new SessionManager(context);
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
}
