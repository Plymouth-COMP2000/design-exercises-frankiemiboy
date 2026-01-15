package com.example.restaurantmanager_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.data.DatabaseHelper;
import com.example.restaurantmanager_app.data.notification.Notification;
import com.example.restaurantmanager_app.data.notification.NotificationDao;
import com.example.restaurantmanager_app.user_management.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class NotificationRecyclerViewManager {

    private RecyclerView recyclerView;
    private NotificationCardAdapter adapter;
    private Context context;
    private List<Notification> notifications;



    public void setup(View rootView, Context context) {
        this.context = context;

        recyclerView = rootView.findViewById(R.id.notification_RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

        loadData();
    }

    public void loadData() {
        SessionManager sessionManager = new SessionManager(context);
        NotificationDao notificationDao = new NotificationDao(context);
        if (sessionManager.getRole().equals("staff")) {
            // Staff members should see all notifications
            notifications = notificationDao.getAllUnreadNotifications();
        } else {
            notifications = notificationDao.getUnreadUserNotifications(sessionManager.getUsername());
        }

        if (notifications == null) {
            notifications = new ArrayList<>();
        }

        adapter = new NotificationCardAdapter(context, notifications, () -> {
            // Reload data when a notification is marked as read
            loadData();

            // Update the Badge in MainActivity
            if (context instanceof MainActivity) {
                ((MainActivity) context).checkNotifications();
            }
        });

        recyclerView.setAdapter(adapter);
    }
}
