
package com.example.restaurantmanager_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.restaurantmanager_app.data.DatabaseHelper;
import com.example.restaurantmanager_app.data.menu.MenuItem;
import com.example.restaurantmanager_app.data.menu.MenuItemDao;
import java.util.List;

public class MenuRecyclerViewManager {

    private RecyclerView recyclerView;
    private MenuCardAdapter adapter;

    public void setup(View rootView, Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        MenuItemDao menuItemDao = new MenuItemDao(context);
        List<MenuItem> fullMenu = menuItemDao.getAllAvailableMenuItems();
        db.close();

        recyclerView = rootView.findViewById(R.id.menu_RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

        adapter = new MenuCardAdapter(context, fullMenu);
        recyclerView.setAdapter(adapter);
    }
}
