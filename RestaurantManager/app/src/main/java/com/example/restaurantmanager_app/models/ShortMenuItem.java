package com.example.restaurantmanager_app.models;

// Model class for a Menu Item
public class ShortMenuItem {
    private int imageResId;  // Image resource ID
    private String title;    // Title text

    public ShortMenuItem(int imageResId, String title) {
        this.imageResId = imageResId;
        this.title = title;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }
}
