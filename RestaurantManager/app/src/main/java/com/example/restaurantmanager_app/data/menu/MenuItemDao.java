package com.example.restaurantmanager_app.data.menu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurantmanager_app.data.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MenuItemDao {
    public static final String TABLE_NAME = "MenuItem";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "description TEXT," +
            "price REAL NOT NULL," +
            "image TEXT," +
            "is_vegan INTEGER DEFAULT 0 NOT NULL," +
            "is_available INTEGER DEFAULT 1 NOT NULL" +
            ")";

    private DatabaseHelper dbHelper;

    public MenuItemDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // READ OPERATIONS
    public List<MenuItem> getAllAvailableMenuItems() {

        List<MenuItem> menuItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve all available menu items;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE is_available = 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                boolean isVegan = cursor.getInt(cursor.getColumnIndexOrThrow("is_vegan")) == 1;
                boolean isAvailable = cursor.getInt(cursor.getColumnIndexOrThrow("is_available")) == 1;

                MenuItem menuItem = new MenuItem(id, name, description, price, image, isVegan, isAvailable);
                menuItems.add(menuItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return menuItems;
    }

    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve all menu items;
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                boolean isVegan = cursor.getInt(cursor.getColumnIndexOrThrow("is_vegan")) == 1;
                boolean isAvailable = cursor.getInt(cursor.getColumnIndexOrThrow("is_available")) == 1;

                MenuItem menuItem = new MenuItem(id, name, description, price, image, isVegan, isAvailable);
                menuItems.add(menuItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return menuItems;
    }


}
