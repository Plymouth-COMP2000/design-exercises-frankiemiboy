package com.example.restaurantmanager_app.notifications;

import android.content.Context;
import android.view.View;
import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.MainActivity;
import com.example.restaurantmanager_app.R;
import com.example.restaurantmanager_app.data.notification.Notification;
import com.example.restaurantmanager_app.data.notification.NotificationDao;
import com.example.restaurantmanager_app.user_management.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationRecyclerViewManager {

    private RecyclerView recyclerView;
    private NotificationCardAdapter adapter;
    private Context context;

    // Thread Management
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

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

        // DB Query runs in the Background
        executor.execute(() -> {
            List<Notification> dbNotifications;

            if (sessionManager.getRole().equals("staff")) {
                // Staff members should see all notifications
                dbNotifications = notificationDao.getAllUnreadNotifications();
            } else {
                dbNotifications = notificationDao.getUnreadUserNotifications(sessionManager.getUsername());
            }

            if (dbNotifications == null) {
                dbNotifications = new ArrayList<>();
            }

            final List<Notification> finalNotifications = dbNotifications;

            // Update UI on Main Thread
            mainHandler.post(() -> {
                updateAdapter(finalNotifications);
            });
        });
    }

    private void updateAdapter(List<Notification> listToShow) {
        if (adapter == null) {
            adapter = new NotificationCardAdapter(context, listToShow, () -> {
                loadData();

                if (context instanceof MainActivity) {
                    // Update Notification Badge in MainActivity
                    ((MainActivity) context).checkNotifications();
                }
            });
            recyclerView.setAdapter(adapter);

        } else {
            adapter.updateData(listToShow);
        }
    }

}
