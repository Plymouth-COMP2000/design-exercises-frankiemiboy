
package com.example.restaurantmanager_app.menu;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.R;
import com.example.restaurantmanager_app.data.menu.MenuItem;
import com.example.restaurantmanager_app.data.menu.MenuService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MenuRecyclerViewManager {

    private RecyclerView recyclerView;
    private MenuCardAdapter adapter;
    private Context context;
    private List<MenuItem> masterMenuItemsList;
    private String currentFilter = "Available";

    // Background Thread
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    // Link to Main Thread (Handler)
    private final Handler mainHandler = new Handler(Looper.getMainLooper());



    public void setup(View rootView, Context context) {
        //this.rootView = rootView;
        this.context = context;
        recyclerView = rootView.findViewById(R.id.menu_RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        loadData();
    }

    public void loadData() {

        // Work for the Background Thread
        executor.execute(() -> {
            MenuService menuService = new MenuService(context);
            List<MenuItem> rawMenuItems = menuService.getAllMenuItems();

            if (rawMenuItems == null) {
                rawMenuItems = new ArrayList<>();
            }

            masterMenuItemsList = rawMenuItems;

            // Post to the Main Thread
            mainHandler.post(() -> {
                filterMenuItems(currentFilter);
            });
        });

    }

    public void filterMenuItems(String filter) {
        this.currentFilter = filter; // Save the current filter

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
        if (adapter == null) {
            adapter = new MenuCardAdapter(context, listToShow);
            recyclerView.setAdapter(adapter);
        }
        else {
            adapter.updateData(listToShow);
        }
    }
}
