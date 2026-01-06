package com.example.restaurantmanager_app;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.data.reservation.Reservation;
import com.example.restaurantmanager_app.data.reservation.ReservationDao;

import java.util.List;

public class ReservationsRecyclerViewManager {
    private RecyclerView recyclerView;
    private ReservationsCardAdapter adapter;

    public void setup(View rootView, Context context) {
        ReservationDao reservationDao = new ReservationDao(context);
        List<Reservation> reservations = reservationDao.getAllReservations();

        recyclerView = rootView.findViewById(R.id.reservation_RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

        adapter = new ReservationsCardAdapter(context, reservations);
        recyclerView.setAdapter(adapter);
    }
}
