package com.example.restaurantmanager_app.models;

// Model class for the data to be displayed in each item
public class SimpleItem {
    private int imageResId;  // Image resource ID
    private String title;    // Title text
    private String description; // Description text

    public SimpleItem(int imageResId, String title, String description) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setAttributes(int imageResId, String title, String description) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
    }
}
