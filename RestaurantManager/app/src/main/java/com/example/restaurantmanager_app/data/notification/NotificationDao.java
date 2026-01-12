package com.example.restaurantmanager_app.data.notification;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurantmanager_app.NotificationsFragment;
import com.example.restaurantmanager_app.data.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;


// Database reading and writing for the notifications table
public class NotificationDao {

    public static final String TABLE_NAME = "Notification";

    // Table Structure
    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username TEXT NOT NULL," +
            "reservation_id INTEGER NOT NULL," +
            "title TEXT NOT NULL," +
            "message TEXT NOT NULL," +
            "is_read INTEGER DEFAULT 0 NOT NULL," +
            "created_at TEXT NOT NULL" +
            ")";

    private DatabaseHelper dbHelper;

    public NotificationDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Read all notifications
    public List<Notification> getAllNotifications() {

        // Implement the logic to retrieve all notifications from the database
        List<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve all notifications;
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                int reservationId = cursor.getInt(cursor.getColumnIndexOrThrow("reservation_id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                int isRead = cursor.getInt(cursor.getColumnIndexOrThrow("is_read"));
                String createdAt = cursor.getString(cursor.getColumnIndexOrThrow("created_at"));

                Notification notification = new Notification(id, username, reservationId, title, message, isRead, createdAt);
                notifications.add(notification);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return notifications;
    }

    // Read user-specific notifications
    public List<Notification> getAllUserNotifications(String username) {

        // Implement the logic to retrieve all notifications that belong to a specific user from the database
        List<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve all notifications;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int reservationId = cursor.getInt(cursor.getColumnIndexOrThrow("reservation_id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                int isRead = cursor.getInt(cursor.getColumnIndexOrThrow("is_read"));
                String createdAt = cursor.getString(cursor.getColumnIndexOrThrow("created_at"));

                Notification notification = new Notification(id, username, reservationId, title, message, isRead, createdAt);
                notifications.add(notification);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return notifications;
    }

    // Read unread user-specific notifications
    public List<Notification> getUnreadUserNotifications(String username) {

        // Implement the logic to retrieve all notifications from the database
        List<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve all notifications;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE is_read = 0 AND user_id = ?";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int reservationId = cursor.getInt(cursor.getColumnIndexOrThrow("reservation_id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                int isRead = cursor.getInt(cursor.getColumnIndexOrThrow("is_read"));
                String createdAt = cursor.getString(cursor.getColumnIndexOrThrow("created_at"));

                Notification notification = new Notification(id, username, reservationId, title, message, isRead, createdAt);
                notifications.add(notification);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return notifications;
    }
}


