package com.example.restaurantmanager_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.data.notification.NotificationPreference;

import java.util.List;

public class NotificationPreferenceAdapter extends RecyclerView.Adapter<NotificationPreferenceAdapter.ItemViewHolder> {

    private Context context;
    private List<NotificationPreference> itemList;

    public NotificationPreferenceAdapter(Context context, List<NotificationPreference> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_preferences_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        NotificationPreference item = itemList.get(position);
        holder.notificationType.setText(item.getNotificationType());
        holder.notificationSwitch.setChecked(item.getEnabled() == 1);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView notificationType;
        SwitchCompat notificationSwitch;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationType = itemView.findViewById(R.id.notificationType);
            notificationSwitch = itemView.findViewById(R.id.notificationSwitch);
        }
    }
}

