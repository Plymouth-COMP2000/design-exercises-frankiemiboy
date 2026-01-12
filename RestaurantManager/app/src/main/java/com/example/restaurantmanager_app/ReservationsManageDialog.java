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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.restaurantmanager_app.data.reservation.Reservation;


public class ReservationsManageDialog extends DialogFragment {

    private static final String ARG_RESERVATION = "reservation";

    public static ReservationsManageDialog newInstance(Reservation reservation) {
        ReservationsManageDialog fragment = new ReservationsManageDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RESERVATION, reservation);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_manage_reservation, container, false);
        ImageButton closeButton = view.findViewById(R.id.manage_reservation_closeButton);
        EditText firstNameInput = view.findViewById(R.id.firstNameInput);
        EditText lastNameInput = view.findViewById(R.id.lastNameInput);
        EditText phoneNumberInput = view.findViewById(R.id.phoneInput);
        EditText dateInput = view.findViewById(R.id.dateInput);
        EditText timeInput = view.findViewById(R.id.timeInput);
        EditText guestsInput = view.findViewById(R.id.guestsInput);
        TextView editReservation = view.findViewById(R.id.editReservation);
        Button confirmButton = view.findViewById(R.id.confirmButton);
        Button cancelReservationButton = view.findViewById(R.id.cancelReservation_Button);

        // Retrieve the MenuItem from the arguments bundle.

        Reservation reservation = getArguments().getParcelable(ARG_RESERVATION);

        if (reservation != null) {
            //firstNameInput.setText(reservation.getUsername());
            //lastNameInput.setText(reservation.getUsername());
            phoneNumberInput.setText(reservation.getUsername());
            dateInput.setText(reservation.getReservation_date());
            timeInput.setText(reservation.getReservation_time());
            guestsInput.setText(String.valueOf(reservation.getParty_size()));
        }

        // User clicks to close dialog
        closeButton.setOnClickListener(v -> dismiss());

        // User makes some changes to reservation details and clicks confirm
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First check for differences in details
                if (
                    !reservation.getReservation_date().equals(dateInput.getText().toString()) ||
                    !reservation.getReservation_time().equals(timeInput.getText().toString()) ||
                    reservation.getParty_size() != Integer.parseInt(guestsInput.getText().toString())
                ) {
                    Reservation newReservation = new Reservation(
                            reservation.getReservationId(),
                            reservation.getUsername(),
                            dateInput.getText().toString(),
                            timeInput.getText().toString(),
                            Integer.parseInt(guestsInput.getText().toString()),
                            reservation.getStatus(),
                            reservation.getCreated_at(),
                            reservation.getLast_modified()
                    );


                }
            }
        });

        return view;
    }


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
