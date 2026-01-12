package com.example.restaurantmanager_app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    private static DatabaseManager instance;
    private static DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    // This is the method you will call from other classes
    public static synchronized DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

    public SQLiteDatabase getDatabase() {
        if (database == null || !database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }
        return database;
    }
}
