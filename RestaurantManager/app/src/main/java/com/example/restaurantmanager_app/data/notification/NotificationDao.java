package com.example.restaurantmanager_app.data.notification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurantmanager_app.NotificationsFragment;
import com.example.restaurantmanager_app.data.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            "created_at TEXT NOT NULL," +

            // Foreign Key Constraint
            "FOREIGN KEY (reservation_id) REFERENCES Reservation(id) ON DELETE CASCADE" +
            ")";

    private DatabaseHelper dbHelper;

    public NotificationDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }


    // ------------------- CREATE ---------------------
    public long createNotification(String username, int reservationId, String title, String message) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("reservation_id", reservationId);
        values.put("title", title);
        values.put("message", message);
        values.put("is_read", 0); // Default to unread

        // Timestamp for created_at column
        String createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        values.put("created_at", createdAt);

        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }


    // ------------------- READ ---------------------
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

    public List<Notification> getAllUnreadNotifications() {

        // Implement the logic to retrieve all notifications from the database
        // Implement the logic to retrieve all notifications from the database
        List<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve all notifications;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE is_read = 0";
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
        String query =  "SELECT * FROM " + TABLE_NAME + " " +
                        "WHERE is_read = 0 AND username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

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


    // -------------------- UPDATE ---------------------
    public int markNotificationAsRead(int notificationId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("is_read", 1);
        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(notificationId) };
        int count = db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
        return count;
    }
}