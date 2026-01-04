package com.example.restaurantmanager_app;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CreateReservationDialog extends DialogFragment {

    public interface ReservationListener {
        void onReservationCreated(
                String name,
                String date,
                String time,
                int guests
        );
    }

    private ReservationListener listener;

    public static CreateReservationDialog newInstance() {
        return new CreateReservationDialog();
    }

    public void setListener(ReservationListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_reservation, container, false);

        EditText name = view.findViewById(R.id.nameInput);
        EditText date = view.findViewById(R.id.dateInput);
        EditText time = view.findViewById(R.id.timeInput);
        EditText guests = view.findViewById(R.id.guestsInput);

        Button cancel = view.findViewById(R.id.cancelButton);
        Button confirm = view.findViewById(R.id.confirmButton);

        cancel.setOnClickListener(v -> dismiss());

        confirm.setOnClickListener(v -> {
            if (listener != null) {
                listener.onReservationCreated(
                        name.getText().toString(),
                        date.getText().toString(),
                        time.getText().toString(),
                        Integer.parseInt(guests.getText().toString())
                );
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

