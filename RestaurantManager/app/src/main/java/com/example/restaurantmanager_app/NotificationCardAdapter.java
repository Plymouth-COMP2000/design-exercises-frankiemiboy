package com.example.restaurantmanager_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.data.notification.Notification;
import com.example.restaurantmanager_app.data.notification.NotificationDao;

import java.util.List;

public class NotificationCardAdapter extends RecyclerView.Adapter<NotificationCardAdapter.ItemViewHolder>{

    private Context context;
    private List<Notification> notificationList;
    private Runnable refreshListener;

    public NotificationCardAdapter(Context context, List<Notification> notificationList, Runnable refreshListener) {
        this.context = context;
        this.notificationList = notificationList;
        this.refreshListener = refreshListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_notification, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Notification notification = notificationList.get(position);

        holder.titleView.setText(notification.getTitle());
        holder.messageView.setText(notification.getMessage());
        try{
            // Try to format the date
            holder.dateView.setText(String.format("%s, %s", notification.getCreatedAt().substring(11, 16), notification.getCreatedAt().substring(0, 10)));
        }
        catch (Exception e) {
            holder.dateView.setText(notification.getCreatedAt());
        }

        holder.markReadButton.setOnClickListener(v -> {
            // Update the Database
            NotificationDao notificationDao = new NotificationDao(context);
            notificationDao.markNotificationAsRead(notification.getNotificationId());

            // Reload data
            if (refreshListener != null) {
                refreshListener.run();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView messageView;
        TextView dateView;
        ImageButton markReadButton;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.notificationTitle);
            messageView = itemView.findViewById(R.id.notificationContent);
            dateView = itemView.findViewById(R.id.notificationTime);
            markReadButton = itemView.findViewById(R.id.mark_read_button);
        }
    }

    public void updateData(List<Notification> newNotificationList) {
        this.notificationList = newNotificationList;
        notifyDataSetChanged(); // Refresh the list
    }
}


