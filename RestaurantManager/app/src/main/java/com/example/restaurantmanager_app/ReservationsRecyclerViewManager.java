package com.example.restaurantmanager_app;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.data.reservation.Reservation;
import com.example.restaurantmanager_app.data.reservation.ReservationDao;
import com.example.restaurantmanager_app.data.reservation.ReservationService;
import com.example.restaurantmanager_app.user_management.SessionManager;

import java.util.List;

public class ReservationsRecyclerViewManager {
    private RecyclerView recyclerView;
    private ReservationsCardAdapter adapter;

    public void setup(View rootView, Context context) {
        SessionManager sessionManager = new SessionManager(context);
        ReservationService reservationService = new ReservationService(context);;

        List<Reservation> reservations;

        // Try to fetch the data from the database
        try {
            // Check user role to determine which reservations to fetch
            if ("staff".equalsIgnoreCase(sessionManager.getRole())) {
                // Staff sees all reservations
                reservations = reservationService.getAllReservations();
            } else {
                // Regular users see only their own reservations
                String username = sessionManager.getUsername();
                reservations = reservationService.getUserReservations(username);
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during data retrieval
            reservations = null;
        }

        recyclerView = rootView.findViewById(R.id.reservation_RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

        adapter = new ReservationsCardAdapter(context, reservations);
        recyclerView.setAdapter(adapter);
    }
}
