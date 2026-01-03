package com.example.restaurantmanager_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.data.DatabaseHelper;
import com.example.restaurantmanager_app.data.notification.Notification;
import com.example.restaurantmanager_app.data.notification.NotificationDao;

import java.util.List;

public class NotificationRecyclerViewManager {

    private RecyclerView recyclerView;
    private NotificationCardAdapter adapter;

    public void setup(View rootView, Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        NotificationDao notificationDao = new NotificationDao(context);
        List<Notification> notifications = notificationDao.getAllNotifications();
        db.close();

        recyclerView = rootView.findViewById(R.id.notification_RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

        adapter = new NotificationCardAdapter(context, notifications);
        recyclerView.setAdapter(adapter);
    }
}
