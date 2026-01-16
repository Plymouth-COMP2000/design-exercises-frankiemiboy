package com.example.restaurantmanager_app.notif_pref;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.R;
import com.example.restaurantmanager_app.data.notification.NotificationPreference;
import com.example.restaurantmanager_app.data.notification.NotificationPreferenceDao;

import java.util.List;

public class NotifPrefRecyclerViewManager {
    private static final String TAG = "NotifPrefRecylerViewMgr";
    private RecyclerView recyclerView;
    private NotificationPreferenceAdapter adapter;

    public void setup(View rootView, Context context) {
        Log.d(TAG, "setup: starting setup");
        NotificationPreferenceDao notificationPreferenceDao = new NotificationPreferenceDao(context);
        List<NotificationPreference> notificationPreferences = notificationPreferenceDao.getAllNotificationPreferences();
        Log.d(TAG, "setup: got " + notificationPreferences.size() + " notification preferences");

        recyclerView = rootView.findViewById(R.id.notificationRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        adapter = new NotificationPreferenceAdapter(context, notificationPreferences);
        recyclerView.setAdapter(adapter);
        Log.d(TAG, "setup: setup complete");
    }
}
