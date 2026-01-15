package com.example.restaurantmanager_app;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.badge.BadgeDrawable;

import com.example.restaurantmanager_app.data.notification.Notification;
import com.example.restaurantmanager_app.data.notification.NotificationDao;
import com.example.restaurantmanager_app.databinding.ActivityMainBinding;
import com.example.restaurantmanager_app.user_management.SessionManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment()); // Default page
        binding.bottomNavigation.setSelectedItemId(R.id.homeFragment);
        sessionManager = new SessionManager(this);
        checkNotifications();

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

        NotificationDao notificationDao = new NotificationDao(this  );
        List<Notification> unreadNotifications = notificationDao.getUnreadUserNotifications(username);
        showNotificationBadge(unreadNotifications.size()); // Show number of unread notifications in the navigation bar
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
