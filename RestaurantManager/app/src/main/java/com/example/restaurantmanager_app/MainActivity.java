package com.example.restaurantmanager_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.restaurantmanager_app.databinding.ActivityMainBinding;
import com.example.restaurantmanager_app.user_management.SessionManager;

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

        /*
        EdgeToEdge.enable(this);
        setContentView(R.layout.settings_page);

        View rootView = findViewById(R.id.main);

        // Original Padding
        int paddingLeft = rootView.getPaddingLeft();
        int paddingTop = rootView.getPaddingTop();
        int paddingRight = rootView.getPaddingRight();
        int paddingBottom = rootView.getPaddingBottom();


        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    paddingLeft + systemBars.left,
                     paddingTop + systemBars.top,
                    paddingRight+ systemBars.right,
                    paddingBottom +  systemBars.bottom
            );
            return insets;
        });
        */

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();

    }

}
