
package com.example.restaurantmanager_app;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.data.menu.MenuItem;
import com.example.restaurantmanager_app.data.menu.MenuService;

import java.util.ArrayList;
import java.util.List;

public class MenuRecyclerViewManager {

    private RecyclerView recyclerView;
    private MenuCardAdapter adapter;
    private Context context;
    private List<MenuItem> masterMenuItemsList;
    private String currentFiler = "Available";



    public void setup(View rootView, Context context) {
        //this.rootView = rootView;
        this.context = context;


        recyclerView = rootView.findViewById(R.id.menu_RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

        loadData();
    }

    public void loadData() {
        MenuService menuService = new MenuService(recyclerView.getContext());
        masterMenuItemsList = menuService.getAllMenuItems();
        filterMenuItems(currentFiler);
    }

    public void filterMenuItems(String filter) {
        this.currentFiler = filter; // Save the current filter

        if(masterMenuItemsList == null) {
            return;
        }

        List<MenuItem> filteredList = new ArrayList<>();

        // Perform filtering based on the availability
        if(filter.equals("Available")) {
            for (MenuItem menuItem : masterMenuItemsList) {
                if (menuItem.isAvailable()) {
                    filteredList.add(menuItem);
                }
            }
            updateAdapter(filteredList);
        }
        else {
            // User selected "All" option, show all relevant menu items
            updateAdapter(masterMenuItemsList);
        }
    }

    public void updateAdapter(List<MenuItem> listToShow) {
        adapter = new MenuCardAdapter(context, listToShow);
        recyclerView.setAdapter(adapter);
    }
}
