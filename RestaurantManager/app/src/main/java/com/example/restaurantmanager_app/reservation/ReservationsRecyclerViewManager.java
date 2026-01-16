package com.example.restaurantmanager_app.reservation;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.R;
import com.example.restaurantmanager_app.data.reservation.Reservation;
import com.example.restaurantmanager_app.data.reservation.ReservationService;
import com.example.restaurantmanager_app.user_management.SessionManager;
import com.example.restaurantmanager_app.user_management.User;
import com.example.restaurantmanager_app.user_management.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ReservationsRecyclerViewManager {
    private RecyclerView recyclerView;
    private ReservationsCardAdapter adapter;
    private Context context;
    //private boolean showConfirmedReservations = true;
    private List<Reservation> masterReservationList;
    private String currentFilter = "Confirmed";

    // Background Thread
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    // Link to Main Thread (Handler)
    private final Handler mainHandler = new Handler(Looper.getMainLooper());


    public void setup(View rootView, Context context) {
        this.context = context;
        recyclerView = rootView.findViewById(R.id.reservation_RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        loadData();
    }


    public void loadData() {
        SessionManager sessionManager = new SessionManager(context);
        ReservationService reservationService = new ReservationService(context);

        executor.execute(() -> {
            List<Reservation> dbReservations;

            // Try to fetch the data from the database
            try {
                // Check user role to determine which reservations to fetch
                // Staff sees all reservations
                // Regular users see  their own reservations only
                if (sessionManager.getRole().equals("staff")) {
                    dbReservations = reservationService.getAllReservations();
                } else {
                    dbReservations = reservationService.getUserReservations(sessionManager.getUsername());
                }
            } catch (Exception e) {
                // Handle any exceptions that may occur during data retrieval
                dbReservations = new ArrayList<>();
                mainHandler.post(() -> {
                    Toast.makeText(context, "Failed to retrieve reservations", Toast.LENGTH_SHORT).show();
                });
            }

            final List<Reservation> initialReservationList = dbReservations;

            mainHandler.post(() -> {
                fetchAndMergeUserData(initialReservationList);
            });
        });
    }

    private void fetchAndMergeUserData(List<Reservation> reservationList) {
        String studentId = "10933458";
        UserService.getAllUsers(context, studentId, new UserService.UsersResponseCallback() {
            @Override
            public void onSuccess(List<User> apiUsers) {
                // MERGE DATA: Map Users to Reservations
                // Convert List<User> to Map<String, User>
                executor.execute(() -> {
                    // Convert List<User> to Map<String, User>
                    Map<String, User> userMap = new HashMap<>();
                    for (User user : apiUsers) {
                        userMap.put(user.getUsername(), user);
                    }

                    // Loop through reservations and inject user details
                    for (Reservation reservation : reservationList) {
                        User user = userMap.get(reservation.getUsername());
                        if (user != null) {
                            reservation.setTransientDetails(user.getFirstName(), user.getLastName(), user.getContact());
                        }
                    }

                    masterReservationList = reservationList;

                    // Update UI with merged data
                    // And filter to show confirmed reservations as default
                    mainHandler.post(() -> {
                        filterReservations(currentFilter);
                    });

                });
            }

            @Override
            public void onError(String message) {
                // If API fetch fails, display reservations without names
                masterReservationList = reservationList;
                mainHandler.post(() -> {
                    filterReservations(currentFilter);
                });
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
        if (adapter == null) {
            adapter = new ReservationsCardAdapter(context, listToShow);
            recyclerView.setAdapter(adapter);
        }
        else {
            adapter.updateData(listToShow);
        }
    }
}
