
package com.example.restaurantmanager_app.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager_app.MainActivity;
import com.example.restaurantmanager_app.R;
import com.example.restaurantmanager_app.models.HomeCardItem;

import java.util.List;

public class HomeCardAdapter extends RecyclerView.Adapter<HomeCardAdapter.ItemViewHolder> {

    private Context context;
    private List<HomeCardItem> itemList;

    public HomeCardAdapter(Context context, List<HomeCardItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_home, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Get the current item
        HomeCardItem item = itemList.get(position);

        // Bind the data to the views
        holder.imageView.setImageResource(item.getImageResId());
        holder.titleView.setText(item.getTitle());
        holder.descriptionView.setText(item.getDescription());

        // When clicked open the appropriate fragment/page
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.replaceFragment(item.getFragment());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class that holds references to the views for each item component
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
