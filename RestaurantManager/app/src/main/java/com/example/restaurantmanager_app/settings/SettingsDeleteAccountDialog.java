package com.example.restaurantmanager_app.settings;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.restaurantmanager_app.LoginActivity;
import com.example.restaurantmanager_app.R;
import com.example.restaurantmanager_app.user_management.AuthCallback;
import com.example.restaurantmanager_app.user_management.SessionManager;
import com.example.restaurantmanager_app.user_management.AuthManager;
import com.example.restaurantmanager_app.data.reservation.ReservationService;
import com.example.restaurantmanager_app.data.notification.NotificationDao;

public class SettingsDeleteAccountDialog extends DialogFragment {

    private SessionManager sessionManager;
    private ReservationService reservationService;
    private NotificationDao notificationDao;
    private AuthManager authManager;

    public static SettingsDeleteAccountDialog newInstance() {
        return new SettingsDeleteAccountDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_delete_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Instantiate necessary objects
        Context context = getContext();
        sessionManager = new SessionManager(context);
        authManager = new AuthManager(context);
        reservationService = new ReservationService(context);
        notificationDao = new NotificationDao(context);

        EditText passwordInput = view.findViewById(R.id.passwordInput);
        Button confirmButton = view.findViewById(R.id.confirmButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(v -> dismiss());

        confirmButton.setOnClickListener(v -> {
            String password = passwordInput.getText().toString().trim();
            if (password.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.equalsIgnoreCase(sessionManager.getPassword())) {
                notificationDao.deleteAllUserNotifications(sessionManager.getUsername());
                reservationService.deleteAllUserReservations(sessionManager.getUsername());
                authManager.deleteAccount("10933458", sessionManager.getUsername(), new AuthCallback() {
                    @Override
                    public void onSuccess(String userType) {
                        authManager.logout();
                        // Set the intent to LoginActivity
                        Intent intent = new Intent(getActivity(), LoginActivity.class);

                        // Destroy any other activities on the stack
                        // Kills the entire application stack, almost like the Login screen is the only screen available
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        // Finish the current activity
                        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                        Toast.makeText(getContext(), "Account Deletion successful", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(getContext(), "Account Deletion failed: " + message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                Toast.makeText(getContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // This method is called after onCreateView() and ensures the dialog is properly sized.
    @Override
    public void onStart() {
        super.onStart();

        // Get the dialog instance.
        Dialog dialog = getDialog();
        if (dialog != null) {
            // Get the window of the dialog.
            Window window = dialog.getWindow();
            if (window != null) {
                // This is necessary to make the dialog match the parent's width.
                // By default, the dialog's width is wrap_content, which can make it appear too slim.
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }
    }
}
