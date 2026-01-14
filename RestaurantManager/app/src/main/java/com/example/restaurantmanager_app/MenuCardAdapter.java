
package com.example.restaurantmanager_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.data.menu.MenuItem;
import com.example.restaurantmanager_app.user_management.SessionManager;

import java.util.List;

public class MenuCardAdapter extends RecyclerView.Adapter<MenuCardAdapter.ItemViewHolder> {

    private Context context;
    private List<MenuItem> itemList;
    SessionManager sessionManager;

    public MenuCardAdapter(Context context, List<MenuItem> itemList) {
        this.context = context;
        this.itemList = itemList;
        sessionManager = new SessionManager(context);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_menu_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        MenuItem item = itemList.get(position);

        holder.imageView.setImageResource(context.getResources().getIdentifier(item.getImageUri(), "drawable", context.getPackageName()));
        holder.titleView.setText(item.getTitle());
        holder.descriptionView.setText(item.getDescription());
        holder.priceView.setText(String.format("£%.2f", item.getPrice()));

        // Set a click listener on the item view.
        holder.itemView.setOnClickListener(v -> {
            // When the item is clicked, create a new instance of the MenuItemDetailsDialog and show it.
            // We pass the MenuItem to the newInstance method, which will be displayed in the dialog.
            // We also need to get the FragmentManager from the context to show the dialog.
            if (sessionManager.getRole().equals("guest")) {
                MenuGuestItemDetailsDialog dialog = MenuGuestItemDetailsDialog.newInstance(item);
                dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "dialog_guest_menu_item");
                Log.d("MenuCardAdapter", "Role is guest");
            }
            else {
                MenuManageItemDialog dialog = MenuManageItemDialog.newInstance(item);
                dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "dialog_staff_menu_item");
                Log.d("MenuCardAdapter", "Role is staff");
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView;
        TextView descriptionView;
        TextView priceView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.menuItemImage);
            titleView = itemView.findViewById(R.id.menuItemTitle);
            descriptionView = itemView.findViewById(R.id.menuItemDescription);
            priceView = itemView.findViewById(R.id.menuItemPrice);
        }
    }
}
