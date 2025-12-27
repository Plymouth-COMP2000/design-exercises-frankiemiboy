package com.example.restaurantmanager_app;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// Import the model class
import com.example.restaurantmanager_app.models.SimpleItem;

public class HomePageLogic extends AppCompatActivity{

    private RecyclerView recyclerView;
    private HomeCardAdapter adapter;
    private List<SimpleItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.home_RecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize data and adapter
        itemList = new ArrayList<>();
        loadData();
        adapter = new HomeCardAdapter(this, itemList);

        recyclerView.setAdapter(adapter);

    }

    private void loadData() {
        // Sample data for testing
        itemList.add(new SimpleItem(R.drawable.ic_launcher_background, "Explore Our Menu", "Take a look at our menu from our different categories, made by our top-level chefs from all across the globe"));
        itemList.add(new SimpleItem(R.drawable.ic_launcher_background, "View Your Reservations", "View the current reservations you have booked and their status"));
        itemList.add(new SimpleItem(R.drawable.ic_launcher_background, "Your Notifications", "View all of your notifications and manage which notification you would like to be notified of"));
    }
}