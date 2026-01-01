package com.example.restaurantmanager_app.data.menu;

public class MenuItem {
    private int menuItemId;
    private String name;
    private String description;
    private double price;
    private String imageUri;
    private boolean isVegan;
    private boolean isAvailable;

    public MenuItem(int menuItemId, String name,
                    String description, double price,
                    String imageUri, boolean isVegan, boolean isAvailable) {

        this.menuItemId = menuItemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUri = imageUri;
        this.isVegan = isVegan;
        this.isAvailable = isAvailable;
    }

    // Getters only (for now)
    public int getMenuItemId() { return menuItemId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImageUri() { return imageUri; }
    public boolean isVegan() { return isVegan; }
    public boolean isAvailable() { return isAvailable; }
}
