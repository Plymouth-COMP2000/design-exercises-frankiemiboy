package com.example.restaurantmanager_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.restaurantmanager_app.user_management.SessionManager;


public class ReservationsFragment extends Fragment {

    private ReservationsRecyclerViewManager reservationRecyclerViewManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservations, container, false);

        // Set up the spinner
        Spinner filterSpinner = view.findViewById(R.id.filterSpinner);

        // Options for the spinner
        String[] filterOptions = {"Confirmed", "All"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, filterOptions);
        filterSpinner.setAdapter(adapter);

        // Selection logic
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFilter = filterOptions[position];

                if (reservationRecyclerViewManager != null) {
                    reservationRecyclerViewManager.filterReservations(selectedFilter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        Button createReservationButton = view.findViewById(R.id.createReservationButton);
        createReservationButton.setOnClickListener(v -> {
            ReservationsCreateDialog dialog = ReservationsCreateDialog.newInstance();
            dialog.show(getParentFragmentManager(), "create_reservation");
        });

        // For this application version, only guests can create reservations
        SessionManager sessionManager = new SessionManager(getContext());
        if (sessionManager.getRole().equals("staff")) {
            createReservationButton.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reservationRecyclerViewManager = new ReservationsRecyclerViewManager();
        reservationRecyclerViewManager.setup(view, getContext());

        // Register Listener for Reservation Updates
        getParentFragmentManager().setFragmentResultListener(
                "request_key_reservation_update",
                this,
                (requestKey, result) -> {
                    if (result.getBoolean("refresh_needed")) {
                        reservationRecyclerViewManager.loadData();
                    }
                }
        );
    }

}