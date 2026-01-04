
package com.example.restaurantmanager_app.data.menu;

import android.os.Parcel;
import android.os.Parcelable;

// A Parcelable is an Android-specific interface for serializing an object so it can be passed between processes or activities.
public class MenuItem implements Parcelable {
    private int menuItemId;
    private String title;
    private String description;
    private double price;
    private String imageUri;
    private boolean isVegan;
    private boolean isAvailable;

    public MenuItem(int menuItemId, String title, String description, double price, String imageUri, boolean isVegan, boolean isAvailable) {
        this.menuItemId = menuItemId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageUri = imageUri;
        this.isVegan = isVegan;
        this.isAvailable = isAvailable;
    }

    // This constructor is used to create a MenuItem from a Parcel.
    protected MenuItem(Parcel in) {
        menuItemId = in.readInt();
        title = in.readString();
        description = in.readString();
        price = in.readDouble();
        imageUri = in.readString();
        isVegan = in.readByte() != 0; // readByte() returns 1 if true, 0 if false
        isAvailable = in.readByte() != 0;
    }

    // This method is used to write the object's data to a Parcel.
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(menuItemId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeString(imageUri);
        dest.writeByte((byte) (isVegan ? 1 : 0)); // writeByte() takes a byte, so we cast the boolean to a byte
        dest.writeByte((byte) (isAvailable ? 1 : 0));
    }

    // This method is used to describe the contents of the Parcelable. It's almost always 0.
    @Override
    public int describeContents() {
        return 0;
    }

    // This CREATOR is a required field for any class that implements Parcelable.
    // It's used to generate instances of your Parcelable class from a Parcel.
    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };

    // Getters only (for now)
    public int getMenuItemId() { return menuItemId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImageUri() { return imageUri; }
    public boolean isVegan() { return isVegan; }
    public boolean isAvailable() { return isAvailable; }
}
