package com.example.restaurantmanager_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.data.notification.NotificationPreference;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public class NotificationPreferenceAdapter extends RecyclerView.Adapter<NotificationPreferenceAdapter.ItemViewHolder> {

    private static final String TAG = "NotifPrefAdapter";
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
        Log.d(TAG, "onCreateViewHolder called");
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder called");
        NotificationPreference item = itemList.get(position);
        Log.d(TAG, "Notification type: " + item.getNotificationType());
        holder.notificationType.setText(item.getNotificationType());
        Log.d(TAG, "Notification Type Successful");
        Log.d(TAG, "Notification enabled: " + item.getEnabled());
        holder.notificationSwitch.setChecked(item.getEnabled() == 1);
        Log.d(TAG, "Switch Successful");
        Log.d(TAG, "onBindViewHolder successful");
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + itemList.size());
        return itemList.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView notificationType;
        SwitchMaterial notificationSwitch;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationType = itemView.findViewById(R.id.notificationType);
            notificationSwitch = itemView.findViewById(R.id.notificationSwitch);
        }
    }
}
