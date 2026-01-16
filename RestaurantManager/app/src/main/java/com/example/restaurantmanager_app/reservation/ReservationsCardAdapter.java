
package com.example.restaurantmanager_app.reservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.R;
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
        holder.nameView.setText(reservation.getFirstName() + " " + reservation.getLastName());
        holder.phoneNumberView.setText(reservation.getPhoneNumber());
        holder.partySizeView.setText(String.valueOf(reservation.getParty_size()));

        // Set a click listener on the item view.
        holder.itemView.setOnClickListener(v -> {
            // When the item is clicked, create a new instance of the ReservationsManageDialog and show it.
            ReservationsManageDialog.newInstance(reservation).show(((AppCompatActivity) context).getSupportFragmentManager(), "dialog_manage_reservation");
        });
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView dateView;
        TextView timeView;
        TextView nameView;
        TextView phoneNumberView;
        TextView partySizeView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            dateView = itemView.findViewById(R.id.reservationDate);
            timeView = itemView.findViewById(R.id.reservationTime);
            nameView = itemView.findViewById(R.id.reservationName);
            phoneNumberView = itemView.findViewById(R.id.reservationPhoneNumber);
            partySizeView = itemView.findViewById(R.id.partySize);
        }
    }

    public void updateData(List<Reservation> newReservationList) {
        this.reservations = newReservationList;
        notifyDataSetChanged(); // Refresh the list
    }

}
