
package com.example.restaurantmanager_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.data.reservation.Reservation;

import java.util.List;

public class ReservationsCardAdapter extends RecyclerView.Adapter<ReservationsCardAdapter.ItemViewHolder> {
    private Context context;
    private List<Reservation> reservations;

    public ReservationsCardAdapter(Context context, List<Reservation> reservations) {
        this.context = context;
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public ReservationsCardAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_reservation, parent, false);
        return new ReservationsCardAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationsCardAdapter.ItemViewHolder holder, int position) {
        Reservation reservation = reservations.get(position);

        holder.dateView.setText(reservation.getReservation_date());
        holder.timeView.setText(reservation.getReservation_time());
        holder.partySizeView.setText(String.valueOf(reservation.getParty_size()));
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView dateView;
        TextView timeView;
        TextView partySizeView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            dateView = itemView.findViewById(R.id.reservationDate);
            timeView = itemView.findViewById(R.id.reservationTime);
            partySizeView = itemView.findViewById(R.id.partySize);
        }
    }
}
