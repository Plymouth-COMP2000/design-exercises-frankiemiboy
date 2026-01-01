package com.example.restaurantmanager_app;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.data.DatabaseHelper;
import com.example.restaurantmanager_app.data.menu.MenuItem;
import com.example.restaurantmanager_app.models.ShortMenuItem;
import com.example.restaurantmanager_app.data.menu.MenuItemDao;

import java.util.ArrayList;
import java.util.List;


public class MenuPageLogic extends AppCompatActivity{

    private RecyclerView recyclerView;
    private MenuCardAdapter adapter;
    private List<ShortMenuItem> shortMenuItemList;

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

        // Initialize the database helper
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        MenuItemDao menuItemDao = new MenuItemDao(this);
        List <MenuItem> fullMenu = menuItemDao.getAllAvailableMenuItems();
        db.close();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.menu_RecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        // Initialize data and adapter
        shortMenuItemList = new ArrayList<>();
        loadData(shortMenuItemList, fullMenu);
        adapter = new MenuCardAdapter(this, shortMenuItemList);

        recyclerView.setAdapter(adapter);

    }

    private void loadData(List<ShortMenuItem> shortMenuItems, List<MenuItem> fullMenuItems) {
        // Sample data for testing
        for (int i = 0; i < fullMenuItems.size(); i++) {
            shortMenuItems.add(new ShortMenuItem(R.drawable.ic_launcher_background, fullMenuItems.get(i).getName()));
        }


    }
}