package com.example.restaurantmanager_app.data.menu;

import android.content.ContentValues;
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
            "title TEXT NOT NULL," +
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

    // ---------------- CREATE OPERATIONS -------------------
    public boolean createMenuItem(String title, String imageUri, double price, String description) {

        // 1. Get the writable database instance using your new DatabaseManager
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 2. Create a ContentValues object to hold the new row's data
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("price", price);
        values.put("image", imageUri);

        // 3. Insert the new row into the menu table
        // db.insert() returns the row ID if successful, -1 if an error occurred
        long newRowId = db.insert(MenuItemDao.TABLE_NAME, null, values);
        db.close();
        return newRowId != -1;
    }


    // ---------------- READ OPERATIONS -------------------
    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve all menu items;
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                boolean isVegan = cursor.getInt(cursor.getColumnIndexOrThrow("is_vegan")) == 1;
                boolean isAvailable = cursor.getInt(cursor.getColumnIndexOrThrow("is_available")) == 1;

                MenuItem menuItem = new MenuItem(id, title, description, price, image, isVegan, isAvailable);
                menuItems.add(menuItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return menuItems;
    }


    // ---------------- UPDATE OPERATIONS -------------------
    public boolean updateMenuItem(int menuItemId, String title, String imageUri, double price, String description) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("price", price);
        values.put("image", imageUri);

        int rowsAffected = db.update(MenuItemDao.TABLE_NAME, values, "id = ?", new String[]{String.valueOf(menuItemId)});
        db.close();
        return rowsAffected > 0;
    }

    public boolean toggleMenuItemAvailability(int menuItemId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Update through the use of pure SQL Logic
        // Was struggling with Content values approach so used pure SQL approach instead
        String sql =    "UPDATE " + MenuItemDao.TABLE_NAME + " "  +
                        "SET is_available = 1 - is_available " +
                        "WHERE id = ?";

        try {
            db.execSQL(sql, new String[]{String.valueOf(menuItemId)});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }


    // ---------------- DELETE OPERATIONS -------------------
    public boolean deleteMenuItem(int menuItemId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = db.delete(MenuItemDao.TABLE_NAME, "id = ?", new String[]{String.valueOf(menuItemId)});
        db.close();
        return rowsAffected > 0;
    }

}
