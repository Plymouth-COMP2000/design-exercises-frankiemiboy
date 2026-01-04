package com.example.restaurantmanager_app.data.notification;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurantmanager_app.data.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class NotificationPreferenceDao {

    public static final String TABLE_NAME = "NotificationPreference";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user_id INTEGER NOT NULL UNIQUE," +
            "new_reservation INTEGER DEFAULT 1 NOT NULL," +
            "cancelled_reservation INTEGER DEFAULT 1 NOT NULL," +
            "changed_reservation INTEGER DEFAULT 1 NOT NULL" +
            ")";

    private DatabaseHelper dbHelper;

    public NotificationPreferenceDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<NotificationPreference> getAllNotificationPreferences() {
        // Implement the logic to retrieve all notification preferences from the database
        List<NotificationPreference> notificationPreferences = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve all notification preferences;
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                int newReservation = cursor.getInt(cursor.getColumnIndexOrThrow("new_reservation"));
                int cancelledReservation = cursor.getInt(cursor.getColumnIndexOrThrow("cancelled_reservation"));
                int changedReservation = cursor.getInt(cursor.getColumnIndexOrThrow("changed_reservation"));

                NotificationPreference notificationPreference = new NotificationPreference(id, userId, newReservation, cancelledReservation, changedReservation);
                notificationPreferences.add(notificationPreference);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return notificationPreferences;
    }

    // Get a specific user's notification preferences
    public NotificationPreference getUserNotificationPreferences(int userId) {
        // Implement the logic to retrieve all notification preferences from the database
        NotificationPreference notificationPreference = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve all notification preferences;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            int newReservation = cursor.getInt(cursor.getColumnIndexOrThrow("new_reservation"));
            int cancelledReservation = cursor.getInt(cursor.getColumnIndexOrThrow("cancelled_reservation"));
            int changedReservation = cursor.getInt(cursor.getColumnIndexOrThrow("changed_reservation"));

            notificationPreference = new NotificationPreference(id, userId, newReservation, cancelledReservation, changedReservation);
        }

        cursor.close();
        return notificationPreference;
    }
}
