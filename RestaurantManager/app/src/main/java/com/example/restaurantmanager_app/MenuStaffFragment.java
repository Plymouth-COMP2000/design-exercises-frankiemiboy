package com.example.restaurantmanager_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MenuStaffFragment extends Fragment {

    private MenuRecyclerViewManager menuRecyclerViewManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_menu, container, false);

        Button addMenuItemButton = view.findViewById(R.id.menu_addMenuItem);
        addMenuItemButton.setOnClickListener(v -> {
            MenuCreateItemDialog dialog = MenuCreateItemDialog.newInstance();
            dialog.show(getParentFragmentManager(), "create_menu_item");
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        menuRecyclerViewManager = new MenuRecyclerViewManager();
        menuRecyclerViewManager.setup(view, getContext());

        // Register Listener for Menu Updates
        getParentFragmentManager().setFragmentResultListener(
                "request_key_menu_update",
                this,
                (requestKey, result) -> {
                    if (result.getBoolean("refresh_needed")) {
                        menuRecyclerViewManager.loadData();
                    }
                }
        );
    }
}
