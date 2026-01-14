
package com.example.restaurantmanager_app;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.restaurantmanager_app.data.DatabaseHelper;
import com.example.restaurantmanager_app.data.menu.MenuItem;
import com.example.restaurantmanager_app.data.menu.MenuItemDao;
import com.example.restaurantmanager_app.data.menu.MenuService;

import java.util.List;

public class MenuRecyclerViewManager {

    private RecyclerView recyclerView;
    private MenuCardAdapter adapter;
    private Context context;
    private View rootView;

    public void setup(View rootView, Context context) {
        this.rootView = rootView;
        this.context = context;


        recyclerView = rootView.findViewById(R.id.menu_RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

        loadData();
    }

    public void loadData() {
        MenuService menuService = new MenuService(recyclerView.getContext());
        List<MenuItem> menuItems = menuService.getAllMenuItems();
        adapter = new MenuCardAdapter(context, menuItems);
        recyclerView.setAdapter(adapter);
    }
}
