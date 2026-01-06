package com.example.restaurantmanager_app.data.notification;

import android.util.Log;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurantmanager_app.data.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;


public class NotificationPreferenceDao {

    private static final String TAG = "NotifPrefDao";
    public static final String TABLE_NAME = "NotificationPreference";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user_id INTEGER NOT NULL," +
            "notification_type TEXT NOT NULL," +
            "enabled INTEGER DEFAULT 1 NOT NULL" +
            ")";

    private DatabaseHelper dbHelper;

    public NotificationPreferenceDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<NotificationPreference> getAllNotificationPreferences() {
        // Implement the logic to retrieve all notification preferences from the database
        Log.d(TAG, "getAllNotificationPreferences called");
        List<NotificationPreference> notificationPreferences = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve all notification preferences;
        String query = "SELECT * FROM " + TABLE_NAME;
        Log.d(TAG, "Query: " + query);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                String notificationType = cursor.getString(cursor.getColumnIndexOrThrow("notification_type"));
                int enabled = cursor.getInt(cursor.getColumnIndexOrThrow("enabled"));

                NotificationPreference notificationPreference = new NotificationPreference(id, userId, notificationType, enabled);
                notificationPreferences.add(notificationPreference);

            } while (cursor.moveToNext());
        }

        cursor.close();
        Log.d(TAG, "Notification preferences retrieved: " + notificationPreferences.size());
        return notificationPreferences;
    }

    // Get a specific user's notification preferences
    public List<NotificationPreference> getUserNotificationPreferences(int userId) {
        // Implement the logic to retrieve all notification preferences from the database
        List<NotificationPreference> notificationPreferences = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve all notification preferences;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do{
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String notificationType = cursor.getString(cursor.getColumnIndexOrThrow("notification_type"));
                int enabled = cursor.getInt(cursor.getColumnIndexOrThrow("enabled"));

                NotificationPreference notificationPreference = new NotificationPreference(id, userId, notificationType, enabled);
                notificationPreferences.add(notificationPreference);
            } while (cursor.moveToNext());

        }

        cursor.close();
        return notificationPreferences;
    }
}
