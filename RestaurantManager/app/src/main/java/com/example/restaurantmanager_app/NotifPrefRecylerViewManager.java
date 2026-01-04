package com.example.restaurantmanager_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.data.DatabaseHelper;
import com.example.restaurantmanager_app.data.notification.NotificationPreference;
import com.example.restaurantmanager_app.data.notification.NotificationPreferenceDao;

import java.util.List;

public class NotifPrefRecylerViewManager {
    private RecyclerView recyclerView;
    private NotificationPreferenceAdapter adapter;

    public void setup(View rootView, Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        NotificationPreferenceDao notificationPreferenceDao = new NotificationPreferenceDao(context);
        List<NotificationPreference> notificationPreferences = notificationPreferenceDao.getAllNotificationPreferences();
        db.close();

        recyclerView = rootView.findViewById(R.id.notificationRecyclerView);
        adapter = new NotificationPreferenceAdapter(context, notificationPreferences);
        recyclerView.setAdapter(adapter);
    }
}
