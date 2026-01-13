package com.example.restaurantmanager_app;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.data.reservation.Reservation;
import com.example.restaurantmanager_app.data.reservation.ReservationDao;
import com.example.restaurantmanager_app.data.reservation.ReservationService;
import com.example.restaurantmanager_app.user_management.SessionManager;
import com.example.restaurantmanager_app.user_management.User;
import com.example.restaurantmanager_app.user_management.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationsRecyclerViewManager {
    private RecyclerView recyclerView;
    private ReservationsCardAdapter adapter;
    private Context context;
    private View rootView;

    public void setup(View rootView, Context context) {
        this.rootView = rootView;
        this.context = context;

        recyclerView = rootView.findViewById(R.id.reservation_RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

        loadData();
    }

    public void loadData() {
        SessionManager sessionManager = new SessionManager(context);
        ReservationService reservationService = new ReservationService(context);;

        List<Reservation> reservations;

        // Try to fetch the data from the database
        try {
            // Check user role to determine which reservations to fetch
            if (sessionManager.getRole().equals("staff")) {
                // Staff sees all reservations
                reservations = reservationService.getAllReservations();
            } else {
                // Regular users see only their own reservations
                String username = sessionManager.getUsername();
                reservations = reservationService.getUserReservations(username);
                Log.d("ReservationsRecyclerViewManager", "Reservations: " + reservations);
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during data retrieval
            reservations = null;
        }

        // Fetch API Users (Asynchronous)
        String studentId = "10933458";
        List<Reservation> finalReservations = reservations;
        UserService.getAllUsers(context, studentId, new UserService.UserResponseCallback() {
            @Override
            public void onSuccess(List<User> apiUsers) {
                // MERGE DATA: Map Users to Reservations
                // Convert List<User> to Map<String, User>
                Map<String, User> userMap = new HashMap<>();
                for (User user : apiUsers) {
                    userMap.put(user.getUsername(), user);
                }

                // Loop through reservations and inject user details
                for (Reservation reservation : finalReservations) {
                    User user = userMap.get(reservation.getUsername());
                    if (user != null) {
                        reservation.setTransientDetails(user.getFirstname(), user.getLastname(), user.getContact());
                    }
                }

                // Update UI with merged data
                adapter = new ReservationsCardAdapter(context, finalReservations);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(String message) {
                // If API fetch fails, display reservations without names
                adapter = new ReservationsCardAdapter(context, finalReservations);
                recyclerView.setAdapter(adapter);
            }

        });

    }
}
