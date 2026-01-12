package com.example.restaurantmanager_app;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurantmanager_app.user_management.SessionManager;


public class SettingsFragment extends Fragment {
    private static final String TAG = "SettingsFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Button saveButton = view.findViewById(R.id.saveButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);
        TextView editDetails = view.findViewById(R.id.editDetails);
        TextView manageNotifications = view.findViewById(R.id.manageNotifications);
        TextView changePassword = view.findViewById(R.id.changePassword);
        TextView logout = view.findViewById(R.id.logout);
        TextView deleteAccount = view.findViewById(R.id.deleteAccount);

        manageNotifications.setOnClickListener(v -> {
            Log.d(TAG, "manageNotifications clicked. Opening NotificationPreferenceFragment dialog.");
            NotificationPreferenceFragment dialog = NotificationPreferenceFragment.newInstance();
            dialog.show(getParentFragmentManager(), "notification_preference_dialog");
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}