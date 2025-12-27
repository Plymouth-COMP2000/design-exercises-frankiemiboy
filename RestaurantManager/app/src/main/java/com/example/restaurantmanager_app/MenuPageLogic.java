package com.example.restaurantmanager_app;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.models.SimpleItem;

import java.util.ArrayList;
import java.util.List;


public class MenuPageLogic extends AppCompatActivity{

    private RecyclerView recyclerView;
    private MenuCardAdapter adapter;
    private List<MenuItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.menu_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.menu_RecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        // Initialize data and adapter
        itemList = new ArrayList<>();
        loadData();
        adapter = new MenuCardAdapter(this, itemList);

        recyclerView.setAdapter(adapter);

    }

    private void loadData() {
        // Sample data for testing
        itemList.add(new MenuItem(R.drawable.ic_launcher_background, "Pork Chops"));
        itemList.add(new MenuItem(R.drawable.ic_launcher_background, "Veggie Roast"));
        itemList.add(new MenuItem(R.drawable.ic_launcher_background, "Your Chicken Roast"));
        itemList.add(new MenuItem(R.drawable.ic_launcher_background, "Basmati Rice"));
        itemList.add(new MenuItem(R.drawable.ic_launcher_background, "Crabs"));
        itemList.add(new MenuItem(R.drawable.ic_launcher_background, "Sushi"));
        itemList.add(new MenuItem(R.drawable.ic_launcher_background, "Pizza"));
        itemList.add(new MenuItem(R.drawable.ic_launcher_background, "Spaghetti Bolognese"));
        itemList.add(new MenuItem(R.drawable.ic_launcher_background, "Toasted Bread"));
    }
}