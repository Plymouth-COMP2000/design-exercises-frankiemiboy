package com.example.restaurantmanager_app;

import android.os.Bundle;
import android.widget.Toast;
import android.os.Looper;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.restaurantmanager_app.home.HomeFragment;
import com.example.restaurantmanager_app.menu.MenuGuestFragment;
import com.example.restaurantmanager_app.menu.MenuStaffFragment;
import com.example.restaurantmanager_app.notifications.NotificationsFragment;
import com.example.restaurantmanager_app.reservation.ReservationsFragment;
import com.example.restaurantmanager_app.settings.SettingsFragment;
import com.google.android.material.badge.BadgeDrawable;

import com.example.restaurantmanager_app.data.notification.Notification;
import com.example.restaurantmanager_app.data.notification.NotificationDao;
import com.example.restaurantmanager_app.databinding.ActivityMainBinding;
import com.example.restaurantmanager_app.user_management.SessionManager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    SessionManager sessionManager;

    // Threading for Badge Checks on Notifications Navigation Icon
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment()); // Default page
            binding.bottomNavigation.setSelectedItemId(R.id.homeFragment);
            checkNotifications();
        }

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            // Cannot use switch case statements, so alternative was if-else-if statements
            if (itemId == R.id.homeFragment) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.menuFragment) {
                // Fragments are different for different roles
                if (sessionManager.getRole().equals("staff")) {
                    replaceFragment(new MenuStaffFragment());
                } else {
                    replaceFragment(new MenuGuestFragment());
                }
            } else if (itemId == R.id.reservationsFragment) {
                replaceFragment(new ReservationsFragment());
            } else if (itemId == R.id.notificationsFragment) {
                replaceFragment(new NotificationsFragment());
            } else if (itemId == R.id.settingsFragment) {
                replaceFragment(new SettingsFragment());
            }

            return true;
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();
    }

    public void checkNotifications() {
        String username = sessionManager.getUsername();

        executor.execute(() -> {
            NotificationDao notificationDao = new NotificationDao(this  );
            List<Notification> unreadNotifications = notificationDao.getUnreadUserNotifications(username);
            int notificationCount = unreadNotifications.size();
            boolean hasUnreadNotifications = notificationCount > 0;

            mainHandler.post(() -> {
                if (hasUnreadNotifications) {
                    Toast.makeText(this, "You have " + notificationCount + " unread notifications", Toast.LENGTH_SHORT).show();
                }
                showNotificationBadge(notificationCount);
            });
        });
    }

    // Shows the number of unread notifications a user has when logging in
    public void showNotificationBadge(int numNotifications) {
        BadgeDrawable badge;
        if (numNotifications > 0) {
            badge = binding.bottomNavigation.getOrCreateBadge(R.id.notificationsFragment);
            badge.setNumber(numNotifications);
            badge.setVisible(true); // Show the badge with number of new notifications in the navigation bar
        }
        else {
            badge = binding.bottomNavigation.getBadge(R.id.notificationsFragment);
            if (badge != null) {
                binding.bottomNavigation.removeBadge(R.id.notificationsFragment); // Remove the badge entirely
            }
        }
    }
}
