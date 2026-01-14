package com.example.restaurantmanager_app;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.restaurantmanager_app.data.menu.MenuService;

// ISSUE FOUND IN THIS CLASS
// TODO Figure out why this dialog keeps crashing when created
public class MenuCreateItemDialog extends DialogFragment {
    public static MenuCreateItemDialog newInstance() {
        return new MenuCreateItemDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_staff_create_menu, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button closeButton = view.findViewById(R.id.create_menu_closeButton);
        EditText menuTitleInput = view.findViewById(R.id.titleInput);
        EditText menuPriceInput = view.findViewById(R.id.priceInput);
        EditText menuDescriptionInput = view.findViewById(R.id.descriptionInput);
        Button saveButton = view.findViewById(R.id.saveButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        // Instantiate necessary objects
        MenuService menuService = new MenuService(getContext());

        closeButton.setOnClickListener(v -> dismiss());
        cancelButton.setOnClickListener(v -> dismiss());

        saveButton.setOnClickListener(v -> {
            String title = menuTitleInput.getText().toString();
            double price = Double.parseDouble(menuPriceInput.getText().toString());
            String description = menuDescriptionInput.getText().toString();

            try {
                if (menuService.createNewMenuItem(title, "prototype_image", price, description)) {
                    Toast toast = Toast.makeText(getContext(), "Menu item created successfully", Toast.LENGTH_SHORT);
                    toast.show();
                    Bundle result = new Bundle();
                    result.putBoolean("refresh_needed", true);
                    getParentFragmentManager().setFragmentResult("request_key_menu_update", result);
                }
                else {
                    Toast toast = Toast.makeText(getContext(), "Ran into issue. Most likely invalid data entered", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                dismiss();
            }
        });
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
