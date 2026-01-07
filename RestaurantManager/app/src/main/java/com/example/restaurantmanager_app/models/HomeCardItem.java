package com.example.restaurantmanager_app.models;

import androidx.fragment.app.Fragment;

// Model class for the data to be displayed in each item
public class HomeCardItem {
    private int imageResId;  // Image resource ID
    private String title;    // Title text
    private String description; // Description text
    private Fragment fragment; // Fragment to be displayed when clicked

    public HomeCardItem(int imageResId, String title, String description, Fragment fragment) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
        this.fragment = fragment;
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
    public Fragment getFragment() { return fragment; }


    public void setAttributes(int imageResId, String title, String description) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
    }
}
