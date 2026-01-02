
package com.example.restaurantmanager_app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.restaurantmanager_app.R;
import com.example.restaurantmanager_app.data.menu.MenuItemDao;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "restaurant.db";
    private static final int DB_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables and populate with sample data;
        db.execSQL(MenuItemDao.CREATE_TABLE_QUERY);
        //db.execSQL(ReservationDao.CREATE_TABLE_QUERY);
        //db.execSQL(NotificationDao.CREATE_TABLE_QUERY);
        insertDummyMenuItems(db);
    }

    private void insertDummyMenuItems(SQLiteDatabase db) {
        // Insert dummy menu items;
        insertMenuItem(db,
                "Margherita Pizza",
                "Classic cheese and tomato pizza",
                8.99,
                "prototype_image",
                1
        );

        insertMenuItem(db,
                "Spaghetti Carbonara",
                "Creamy pasta with bacon and parmesan",
                10.50,
                "prototype_image",
                1
        );

        insertMenuItem(db,
                "Caesar Salad",
                "Romaine lettuce with Caesar dressing",
                6.75,
                "prototype_image",
                1
        );
    }

    private void insertMenuItem(SQLiteDatabase db, String title, String description, double price, String image, int isVegan) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("price", price);
        values.put("image", image);
        values.put("is_vegan", isVegan);
        values.put("is_available", 1);

        db.insert(MenuItemDao.TABLE_NAME, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed;
        db.execSQL("DROP TABLE IF EXISTS " + MenuItemDao.TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + ReservationDao.TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + NotificationDao.TABLE_NAME);
        onCreate(db);
    }
}
