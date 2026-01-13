package com.example.restaurantmanager_app;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.restaurantmanager_app.user_management.SessionManager;
//import com.example.restaurantmanager_app.data.reservation.Reservation;
import com.example.restaurantmanager_app.data.reservation.ReservationService;

public class ReservationsCreateDialog extends DialogFragment {

    private SessionManager sessionManager;

    public static ReservationsCreateDialog newInstance() {
        return new ReservationsCreateDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_reservation, container, false);

        EditText firstName = view.findViewById(R.id.firstNameInput);
        EditText lastName = view.findViewById(R.id.lastNameInput);
        EditText phoneNumber = view.findViewById(R.id.phoneNumberInput);
        EditText date = view.findViewById(R.id.dateInput);
        EditText time = view.findViewById(R.id.timeInput);
        EditText guests = view.findViewById(R.id.guestsInput);

        Button cancel = view.findViewById(R.id.cancelButton);
        ImageButton close = view.findViewById(R.id.manage_reservation_closeButton);
        Button confirm = view.findViewById(R.id.confirmButton);

        // Instantiate necessary objects
        sessionManager = new SessionManager(getContext());
        ReservationService reservationService = new ReservationService(getContext());

        // Pre-populate obvious data
        firstName.setText(sessionManager.getFirstName());
        lastName.setText(sessionManager.getLastName());
        phoneNumber.setText(sessionManager.getPhoneNumber());

        // Set OnClick Listeners
        cancel.setOnClickListener(v -> dismiss());
        close.setOnClickListener(v -> dismiss());

        confirm.setOnClickListener(v -> {
            // Try to create a new reservation
            try {
                if (reservationService.createNewReservation(
                        sessionManager.getUsername(),
                        date.getText().toString(),
                        time.getText().toString(),
                        Integer.parseInt(guests.getText().toString()))
                        != -1) {
                    // If successful, display success message
                    Toast toast = Toast.makeText(getContext(), "Reservation created successfully", Toast.LENGTH_SHORT);
                    toast.show();
                    Bundle result = new Bundle();
                    result.putBoolean("refresh_needed", true);
                } else {
                    // If unsuccessful, display error message
                    Toast toast = Toast.makeText(getContext(), "Ran into issue. Most likely invalid data entered", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            dismiss();
        });

        return view;
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

