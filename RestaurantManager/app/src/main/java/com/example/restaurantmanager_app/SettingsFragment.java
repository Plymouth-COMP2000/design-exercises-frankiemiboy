package com.example.restaurantmanager_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantmanager_app.data.notification.NotificationDao;
import com.example.restaurantmanager_app.data.reservation.ReservationService;
import com.example.restaurantmanager_app.user_management.AuthCallback;
import com.example.restaurantmanager_app.user_management.AuthManager;
import com.example.restaurantmanager_app.user_management.SessionManager;
import com.example.restaurantmanager_app.user_management.User;

import java.util.Objects;


public class SettingsFragment extends Fragment {
    private static final String TAG = "SettingsFragment";
    private SessionManager sessionManager;
    private AuthManager authManager;
    private ReservationService reservationService;
    private NotificationDao notificationDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Instantiate the SessionManager and AuthManager for Detail editing and logout procedure
        sessionManager = new SessionManager(getContext());
        authManager = new AuthManager(getContext());
        reservationService = new ReservationService(getContext());
        notificationDao = new NotificationDao(getContext());

        // Find all views
        EditText emailInput = view.findViewById(R.id.emailInput);
        EditText phoneNumberInput = view.findViewById(R.id.phoneNumberInput);
        EditText firstNameInput = view.findViewById(R.id.firstNameInput);
        EditText lastNameInput = view.findViewById(R.id.lastNameInput);

        TextView editDetails = view.findViewById(R.id.editDetails);
        Button saveButton = view.findViewById(R.id.saveButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        TextView manageNotifications = view.findViewById(R.id.manageNotifications);
        TextView logout = view.findViewById(R.id.logout);
        TextView deleteAccount = view.findViewById(R.id.deleteAccount);

        //  Restrict deletion of staff accounts to administrative persons only
        if ("staff".equalsIgnoreCase(sessionManager.getRole())) {
            deleteAccount.setVisibility(View.GONE);
        }

        emailInput.setText(sessionManager.getEmail());
        phoneNumberInput.setText(sessionManager.getPhoneNumber());
        firstNameInput.setText(sessionManager.getFirstName());
        lastNameInput.setText(sessionManager.getLastName());

        // SET UP CLICK LISTENERS
        // Editing User Details
        editDetails.setOnClickListener(v -> {
            emailInput.setEnabled(true);
            phoneNumberInput.setEnabled(true);
            firstNameInput.setEnabled(true);
            lastNameInput.setEnabled(true);
            editDetails.setVisibility(View.GONE);
            saveButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.VISIBLE);
        });

        // Cancel Editing User Details
        cancelButton.setOnClickListener(v -> {
            emailInput.setEnabled(false);
            phoneNumberInput.setEnabled(false);
            firstNameInput.setEnabled(false);
            lastNameInput.setEnabled(false);
            editDetails.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.GONE);
            cancelButton.setVisibility(View.GONE);
        });

        // Saving Changes to User Details
        saveButton.setOnClickListener(v -> {

            // 1. Collect Input Data
            String newFirstName = firstNameInput.getText().toString();
            String newLastName = lastNameInput.getText().toString();
            String newEmail = emailInput.getText().toString();
            String newPhoneNumber = phoneNumberInput.getText().toString();

            // 2. Complete User Object attributes
            String username = sessionManager.getUsername();
            String password = sessionManager.getPassword();
            String role = sessionManager.getRole();

            // 3. Create User Object
            User user = new User(
                    username,
                    password,
                    newFirstName,
                    newLastName,
                    newEmail,
                    newPhoneNumber,
                    role
            );

            // 4. Request Update from API
            authManager.updateUserDetails("10933458", username, user, new AuthCallback() {
                @Override
                public void onSuccess(String userType) {
                    // Session manager is already updated inside AUthManager

                    // Exit edit mode
                    emailInput.setEnabled(false);
                    phoneNumberInput.setEnabled(false);
                    firstNameInput.setEnabled(false);
                    lastNameInput.setEnabled(false);
                    editDetails.setVisibility(View.VISIBLE);
                    saveButton.setVisibility(View.GONE);
                    cancelButton.setVisibility(View.GONE);

                    Toast.makeText(getContext(), "Details updated successfully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(getContext(), "Update failed: " + message, Toast.LENGTH_SHORT).show();
                }
            }
            );


            emailInput.setEnabled(false);
            phoneNumberInput.setEnabled(false);
            firstNameInput.setEnabled(false);
            lastNameInput.setEnabled(false);
            editDetails.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.GONE);
            cancelButton.setVisibility(View.GONE);
        });

        manageNotifications.setOnClickListener(v -> {
            Log.d(TAG, "manageNotifications clicked. Opening NotificationPreferenceFragment dialog.");
            NotificationPreferenceFragment dialog = NotificationPreferenceFragment.newInstance();
            dialog.show(getParentFragmentManager(), "notification_preference_dialog");
        });

        // User logouts out account
        logout.setOnClickListener(v -> {
            authManager.logout(); // This clear the current session

            // Set the intent to LoginActivity
            Intent intent = new Intent(getActivity(), LoginActivity.class);

            // Destroy any other activities on the stack
            // Kills the entire application stack, almost like the Login screen is the only screen available
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            // Finish the current activity
            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            Toast.makeText(getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
        });

        // Delete account logic
        deleteAccount.setOnClickListener(v -> {
            // Take user to final dialog to confirm their action
            SettingsDeleteAccountDialog dialog = SettingsDeleteAccountDialog.newInstance();
            dialog.show(getParentFragmentManager(), "delete_account_dialog");
        });
    }
}