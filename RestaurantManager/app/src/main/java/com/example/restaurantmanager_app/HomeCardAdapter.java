package com.example.restaurantmanager_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


// Model class for the data to be displayed in each item
class Item {
    int imageResId;  // Image resource ID
    String title;    // Title text
    String description; // Description text

    Item(int imageResId, String title, String description) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
    }
}


public class HomeCardAdapter extends RecyclerView.Adapter<HomeCardAdapter.ItemViewHolder> {

    private Context context;
    private List<Item> itemList;


    public HomeCardAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_card, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        // Get the current item
        Item item = itemList.get(position);

        // Bind data to the views
        holder.imageView.setImageResource(item.imageResId);
        holder.titleView.setText(item.title);
        holder.descriptionView.setText(item.description);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    // ViewHolder class that holds references to the views for each item
    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView;
        TextView descriptionView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.homeItemImage);
            titleView = itemView.findViewById(R.id.homeItemTitle);
            descriptionView = itemView.findViewById(R.id.homeItemDescription);
        }
    }
}