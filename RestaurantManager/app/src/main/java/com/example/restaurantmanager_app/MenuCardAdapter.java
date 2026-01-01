package com.example.restaurantmanager_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.models.ShortMenuItem;

import java.util.List;


public class MenuCardAdapter extends RecyclerView.Adapter<MenuCardAdapter.ItemViewHolder> {

    private Context context;
    private List<ShortMenuItem> itemList;


    public MenuCardAdapter(Context context, List<ShortMenuItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item_card, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        // Get the current item
        ShortMenuItem item = itemList.get(position);

        // Bind data to the views
        holder.imageView.setImageResource(item.getImageResId());
        holder.titleView.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    // ViewHolder class that holds references to the views for each item
    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.menuItemImage);
            titleView = itemView.findViewById(R.id.menuItemTitle);
        }
    }
}