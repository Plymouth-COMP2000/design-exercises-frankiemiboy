
package com.example.restaurantmanager_app;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.restaurantmanager_app.models.HomeCardItem;
import java.util.ArrayList;
import java.util.List;

public class HomeRecyclerViewManager {

    private RecyclerView recyclerView;
    private HomeCardAdapter adapter;
    private List<HomeCardItem> itemList;

    public void setup(View rootView, Context context) {
        recyclerView = rootView.findViewById(R.id.home_RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));

        itemList = new ArrayList<>();
        loadData();
        adapter = new HomeCardAdapter(context, itemList);
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        itemList.add(new HomeCardItem(R.drawable.ic_launcher_background, "Explore Our Menu", "Take a look at our menu from our different categories, made by our top-level chefs from all across the globe", new MenuFragment()));
        itemList.add(new HomeCardItem(R.drawable.ic_launcher_background, "View Your Reservations", "View the current reservations you have booked and their status", new ReservationsFragment()));
        itemList.add(new HomeCardItem(R.drawable.ic_launcher_background, "Your Notifications", "View all of your notifications and manage which notification you would like to be notified of", new NotificationsFragment()));
    }
}
