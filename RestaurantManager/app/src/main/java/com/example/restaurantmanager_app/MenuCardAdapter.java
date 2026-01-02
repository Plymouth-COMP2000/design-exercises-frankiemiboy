
package com.example.restaurantmanager_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.data.menu.MenuItem;

import java.util.List;

public class MenuCardAdapter extends RecyclerView.Adapter<MenuCardAdapter.ItemViewHolder> {

    private Context context;
    private List<MenuItem> itemList;

    public MenuCardAdapter(Context context, List<MenuItem> itemList) {
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
        MenuItem item = itemList.get(position);

        holder.imageView.setImageResource(context.getResources().getIdentifier(item.getImageUri(), "drawable", context.getPackageName()));
        holder.titleView.setText(item.getTitle());
        holder.descriptionView.setText(item.getDescription());
        holder.priceView.setText(String.format("£%.2f", item.getPrice()));
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
