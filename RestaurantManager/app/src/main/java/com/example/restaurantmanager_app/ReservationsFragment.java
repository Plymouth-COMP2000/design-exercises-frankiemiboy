package com.example.restaurantmanager_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ReservationsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservations, container, false);

        Button createReservationButton = view.findViewById(R.id.createReservationButton);
        createReservationButton.setOnClickListener(v -> {
            ReservationsCreateDialog dialog = ReservationsCreateDialog.newInstance();
            dialog.setListener((name, date, time, guests) -> {
                Log.d("Reservation", name + " " + date + " " + time + " " + guests);
            });

            dialog.show(getParentFragmentManager(), "create_reservation");
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ReservationsRecyclerViewManager reservationRecyclerViewManager = new ReservationsRecyclerViewManager();
        reservationRecyclerViewManager.setup(view, getContext());
    }

}