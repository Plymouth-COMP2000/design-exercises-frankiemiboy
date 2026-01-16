
package com.example.restaurantmanager_app.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.restaurantmanager_app.R;
import com.example.restaurantmanager_app.reservation.ReservationsCreateDialog;

public class MenuGuestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_menu, container, false);

        Button createReservationButton = view.findViewById(R.id.menu_createReservationButton);
        createReservationButton.setOnClickListener(v -> {
            ReservationsCreateDialog dialog = ReservationsCreateDialog.newInstance();
            dialog.show(getParentFragmentManager(), "create_reservation");
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MenuRecyclerViewManager menuRecyclerViewManager = new MenuRecyclerViewManager();
        menuRecyclerViewManager.setup(view, getContext());
    }

}
