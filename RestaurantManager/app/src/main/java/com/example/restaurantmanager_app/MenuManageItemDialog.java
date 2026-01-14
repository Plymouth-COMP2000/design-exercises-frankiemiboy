package com.example.restaurantmanager_app;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.restaurantmanager_app.data.menu.MenuItem;
import com.example.restaurantmanager_app.data.menu.MenuService;

public class MenuManageItemDialog extends DialogFragment {
    // A constant to use as a key for the MenuItem in the arguments bundle.
    private static final String ARG_MENU_ITEM = "menu_item";


    // A factory method to create a new instance of this dialog, which allows us to pass in a MenuItem.
    public static MenuManageItemDialog newInstance(MenuItem menuItem) {
        MenuManageItemDialog fragment = new MenuManageItemDialog();
        Bundle args = new Bundle();
        // The MenuItem is put into the arguments bundle. This is the standard way to pass data to a fragment.
        args.putParcelable(ARG_MENU_ITEM, menuItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_staff_menu_item, container, false);

        // Get references to the views in the dialog layout.
        ImageButton closeButton = view.findViewById(R.id.manage_menu_closeButton);
        ImageView imageView = view.findViewById(R.id.itemImage);
        EditText titleInput = view.findViewById(R.id.titleInput);
        EditText priceInput = view.findViewById(R.id.priceInput);
        EditText descriptionInput = view.findViewById(R.id.descriptionInput);
        TextView editMenuItem = view.findViewById(R.id.editMenuItem);
        TextView availability = view.findViewById(R.id.availability);
        Button saveButton = view.findViewById(R.id.saveButton);
        Button setAvailabilityButton = view.findViewById(R.id.setAvailabilityButton);
        Button deleteButton = view.findViewById(R.id.deleteButton);


        // Retrieve the MenuItem from the arguments bundle.
        MenuItem menuItem = getArguments().getParcelable(ARG_MENU_ITEM);
        MenuService menuService = new MenuService(getContext());


        // If the MenuItem is not null, populate the views with its data.
        if (menuItem != null) {
            titleInput.setText(menuItem.getTitle());
            imageView.setImageResource(getContext().getResources().getIdentifier(menuItem.getImageUri(), "drawable", getContext().getPackageName()));
            priceInput.setText(String.format("£%.2f", menuItem.getPrice()));
            descriptionInput.setText(menuItem.getDescription());
            if (menuItem.isAvailable()) {
                availability.setText("AVAILABLE");
            } else {
                availability.setText("UNAVAILABLE");
            }
        }

        // Set a click listener on the close button to dismiss the dialog.
        closeButton.setOnClickListener(v -> {
            Log.d("MenuManageItemDialog", "Close button clicked");
            dismiss();
        });

        // Click listener to change availability
        setAvailabilityButton.setOnClickListener(v -> {
            if (menuItem.isAvailable()) {
                availability.setText("UNAVAILABLE");
                menuItem.setAvailable(false);
            } else {
                availability.setText("AVAILABLE");
                menuItem.setAvailable(true);
            }
            menuService.toggleMenuItemAvailability(menuItem.getMenuItemId());
        });

        // Click listener to enable editing
        editMenuItem.setOnClickListener(v -> {
            titleInput.setEnabled(true);
            priceInput.setEnabled(true);
            descriptionInput.setEnabled(true);
            editMenuItem.setVisibility(View.GONE);
        });

        // Click listener for confirming changes
        saveButton.setOnClickListener(v -> {
            try {
                if (menuService.updateExistingMenuItem(
                        menuItem.getMenuItemId(),
                        titleInput.getText().toString(),
                        "prototype_image",
                        Double.parseDouble(priceInput.getText().toString()),
                        descriptionInput.getText().toString())
                ) {
                    Toast toast = Toast.makeText(getContext(), "Menu item updated successfully", Toast.LENGTH_SHORT);
                    toast.show();
                    Bundle result = new Bundle();
                    result.putBoolean("refresh_needed", true);
                    getParentFragmentManager().setFragmentResult("request_key_menu_update", result);
                }
                else {
                    Toast toast = Toast.makeText(getContext(), "Failed to update menu item", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } catch (Exception e) {
                // Handle any exceptions that may occur during data retrieval
                e.printStackTrace();
            } finally {
                dismiss();

            }

        });
        Log.d("MenuManageItemDialog", "onCreateView() called");
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
