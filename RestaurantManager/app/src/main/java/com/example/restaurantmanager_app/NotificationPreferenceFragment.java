package com.example.restaurantmanager_app;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NotificationPreferenceFragment extends DialogFragment {

    private static final String TAG = "NotificationPrefFrag";

    public static NotificationPreferenceFragment newInstance() {
        return new NotificationPreferenceFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: creating view");
        View view = inflater.inflate(R.layout.fragment_notification_preferences, container, false);

        ImageButton closeButton = view.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> {
            Log.d(TAG, "closeButton clicked. Dismissing dialog.");
            dismiss();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: setting up recycler view");
        NotifPrefRecyclerViewManager notifPrefRecylerViewManager = new NotifPrefRecyclerViewManager();
        notifPrefRecylerViewManager.setup(view, getContext());
        Log.d(TAG, "onViewCreated: recycler view setup complete");
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
