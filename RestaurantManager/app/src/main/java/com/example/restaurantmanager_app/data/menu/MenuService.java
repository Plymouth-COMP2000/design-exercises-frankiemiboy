package com.example.restaurantmanager_app.data.menu;

import android.content.Context;
import android.util.Log;

import java.util.List;

public class MenuService {

    private MenuItemDao menuItemDao;

    public MenuService(Context context) {
        this.menuItemDao = new MenuItemDao(context);
    }

    // CRUD OPERATIONS

    // -------------- CREATE ------------------
    public boolean createNewMenuItem(String title, String imageUri, double price, String description) {
        return menuItemDao.createMenuItem(title, imageUri, price, description);
    }

    // -------------- READ ------------------
    public List<MenuItem> getAllMenuItems() {
        return menuItemDao.getAllMenuItems();
    }

    // -------------- UPDATE ------------------
    public boolean updateExistingMenuItem(int menuItemId, String title, String imageUri, double price, String description) {
        return menuItemDao.updateMenuItem(menuItemId, title, imageUri, price, description);
    }

    public boolean toggleMenuItemAvailability(int menuItemId) {
        boolean value = menuItemDao.toggleMenuItemAvailability(menuItemId);
        Log.d("MenuService", "toggleMenuItemAvailability: " + value);
        return value;
    }


    // -------------- DELETE ------------------
    public boolean deleteExistingMenuItem(int menuItemId) {
        return menuItemDao.deleteMenuItem(menuItemId);
    }

}
