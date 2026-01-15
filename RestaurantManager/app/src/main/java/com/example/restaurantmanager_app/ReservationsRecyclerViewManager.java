package com.example.restaurantmanager_app;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.data.reservation.Reservation;
import com.example.restaurantmanager_app.data.reservation.ReservationService;
import com.example.restaurantmanager_app.user_management.SessionManager;
import com.example.restaurantmanager_app.user_management.User;
import com.example.restaurantmanager_app.user_management.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationsRecyclerViewManager {
    private RecyclerView recyclerView;
    private ReservationsCardAdapter adapter;
    private Context context;
    //private boolean showConfirmedReservations = true;
    private List<Reservation> masterReservationList;
    private String currentFilter = "Confirmed";


    public void setup(View rootView, Context context) {
        this.context = context;
        recyclerView = rootView.findViewById(R.id.reservation_RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        loadData();
    }

    /*
    public void toggleReservationView() {
        showConfirmedReservations = !showConfirmedReservations;
        loadData();
    }
     */

    public void loadData() {
        SessionManager sessionManager = new SessionManager(context);
        ReservationService reservationService = new ReservationService(context);

        List<Reservation> reservations;

        // Try to fetch the data from the database
        try {
            // Check user role to determine which reservations to fetch
            // Staff sees all reservations
            // Regular users see  their own reservations only
            if (sessionManager.getRole().equals("staff")) {
                reservations = reservationService.getAllReservations();
            } else {
                reservations = reservationService.getUserReservations(sessionManager.getUsername());
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during data retrieval
            reservations = null;
            Toast.makeText(context, "Failed to retrieve reservations", Toast.LENGTH_SHORT).show();
        }

        // Fetch API Users (Asynchronous)
        String studentId = "10933458";
        masterReservationList = reservations;
        UserService.getAllUsers(context, studentId, new UserService.UsersResponseCallback() {
            @Override
            public void onSuccess(List<User> apiUsers) {
                // MERGE DATA: Map Users to Reservations
                // Convert List<User> to Map<String, User>
                Map<String, User> userMap = new HashMap<>();
                for (User user : apiUsers) {
                    userMap.put(user.getUsername(), user);
                }

                // Loop through reservations and inject user details
                for (Reservation reservation : masterReservationList) {
                    User user = userMap.get(reservation.getUsername());
                    if (user != null) {
                        reservation.setTransientDetails(user.getFirstName(), user.getLastName(), user.getContact());
                    }
                }

                // Update UI with merged data
                // And filter to show confirmed reservations as default
                filterReservations(currentFilter);
            }

            @Override
            public void onError(String message) {
                // If API fetch fails, display reservations without names
                adapter = new ReservationsCardAdapter(context, masterReservationList);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public void filterReservations(String filter) {
        this.currentFilter = filter; // Save the current filter

        if (masterReservationList == null) {
            return;
        }

        List<Reservation> filteredList = new ArrayList<>();

        if (filter.equals("Confirmed")) {
            for (Reservation reservation : masterReservationList) {
                if ("confirmed".equalsIgnoreCase(reservation.getStatus())) {
                    filteredList.add(reservation);
                }
            }
            updateAdapter(filteredList);
        }
        else {
            // User selected "All" option, show all relevant reservations
            updateAdapter(masterReservationList);
        }
    }

    private void updateAdapter(List<Reservation> listToShow) {
        adapter = new ReservationsCardAdapter(context, listToShow);
        recyclerView.setAdapter(adapter);
    }
}
