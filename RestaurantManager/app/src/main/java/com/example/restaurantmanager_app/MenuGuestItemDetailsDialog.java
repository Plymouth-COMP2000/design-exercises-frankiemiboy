
package com.example.restaurantmanager_app;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.restaurantmanager_app.data.menu.MenuItem;

public class MenuGuestItemDetailsDialog extends DialogFragment {

    // A constant to use as a key for the MenuItem in the arguments bundle.
    private static final String ARG_MENU_ITEM = "menu_item";

    // A factory method to create a new instance of this dialog, which allows us to pass in a MenuItem.
    public static MenuGuestItemDetailsDialog newInstance(MenuItem menuItem) {
        MenuGuestItemDetailsDialog fragment = new MenuGuestItemDetailsDialog();
        Bundle args = new Bundle();
        // The MenuItem is put into the arguments bundle. This is the standard way to pass data to a fragment.
        args.putParcelable(ARG_MENU_ITEM, menuItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_guest_menu_item, container, false);

        // Get references to the views in the dialog layout.
        ImageButton closeButton = view.findViewById(R.id.menu_item_details_closeButton);
        TextView titleView = view.findViewById(R.id.itemTitle);
        ImageView imageView = view.findViewById(R.id.itemImage);
        TextView priceView = view.findViewById(R.id.itemPrice);
        TextView descriptionView = view.findViewById(R.id.itemDescription);

        // Retrieve the MenuItem from the arguments bundle.
        MenuItem menuItem = getArguments().getParcelable(ARG_MENU_ITEM);

        // If the MenuItem is not null, populate the views with its data.
        if (menuItem != null) {
            titleView.setText(menuItem.getTitle());
            imageView.setImageResource(getContext().getResources().getIdentifier(menuItem.getImageUri(), "drawable", getContext().getPackageName()));
            priceView.setText(String.format("£%.2f", menuItem.getPrice()));
            descriptionView.setText(menuItem.getDescription());
        }

        // Set a click listener on the close button to dismiss the dialog.
        closeButton.setOnClickListener(v -> dismiss());

        return view;
    }

    // This method is called after onCreateView() and ensures the dialog is properly sized.
    @Override
    public void onStart() {
        super.onStart();

        // Get the dialog instance.
        Dialog dialog = getDialog();
        if (dialog != null) {
            // Get the window of the dialog.
            Window window = dialog.getWindow();
            if (window != null) {
                // This is necessary to make the dialog match the parent's width.
                // By default, the dialog's width is wrap_content, which can make it appear too slim.
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }
    }
}
