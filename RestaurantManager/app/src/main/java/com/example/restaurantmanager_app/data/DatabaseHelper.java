
package com.example.restaurantmanager_app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.restaurantmanager_app.data.menu.MenuItemDao;
import com.example.restaurantmanager_app.data.notification.NotificationDao;
import com.example.restaurantmanager_app.data.notification.NotificationPreferenceDao;
import com.example.restaurantmanager_app.data.reservation.ReservationDao;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "restaurant.db";
    private static final int DB_VERSION = 8; // Incremented version

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables and populate with sample data
        db.execSQL(MenuItemDao.CREATE_TABLE_QUERY);
        db.execSQL(ReservationDao.CREATE_TABLE_QUERY);
        db.execSQL(NotificationDao.CREATE_TABLE_QUERY);
        db.execSQL(NotificationPreferenceDao.CREATE_TABLE_QUERY);

        insertDummyMenuItems(db);
        insertDummyReservations(db);
        insertDummyNotifications(db);
        insertDummyNotificationPreferences(db);
    }

    private void insertDummyMenuItems(SQLiteDatabase db) {
        insertMenuItem(db, "Margherita Pizza", "Classic cheese and tomato pizza", 8.99, "prototype_image", 1);
        insertMenuItem(db, "Spaghetti Carbonara", "Creamy pasta with bacon and parmesan", 10.50, "prototype_image", 1);
        insertMenuItem(db, "Caesar Salad", "Romaine lettuce with Caesar dressing", 6.75, "prototype_image", 1);
    }

    private void insertDummyNotifications(SQLiteDatabase db) {
        // Corrected dummy notifications
        insertNotification(db, 1, 1, "Reservation Confirmation", "Your reservation has been confirmed!", "2023-08-25 10:30:00");
        insertNotification(db, 2, 2, "New Menu Item", "We've added a new item to our menu!", "2023-08-29 11:45:00");
        insertNotification(db, 3, 3, "Reservation Cancellation", "Your reservation has been cancelled.", "2023-08-27 12:05:00");
    }

    private void insertDummyNotificationPreferences(SQLiteDatabase db) {
        // Corrected dummy notification preferences
        insertNotificationPreference(db, 1, "Cancelled Reservation", 1);
        insertNotificationPreference(db, 2, "New Reservation", 1);
        insertNotificationPreference(db, 3, "Reservation Confirmation", 0);
        insertNotificationPreference(db, 3, "New Menu Item", 0);
        insertNotificationPreference(db, 2, "Cancelled Reservation", 0);
    }

    private void insertDummyReservations(SQLiteDatabase db) {
        insertReservation(db, 1, "2023-08-25", "18:30", 4, "confirmed", "2023-08-25 10:30:00", "2023-08-25 10:30:00");
        insertReservation(db, 2, "2023-08-26", "19:00", 2, "confirmed", "2023-08-26 11:45:00", "2023-08-26 11:45:00");
        insertReservation(db, 3, "2023-08-29", "20:15", 6, "confirmed", "2023-08-29 12:05:00", "2023-08-29 12:05:00");
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

    // Corrected insertNotification method
    private void insertNotification(SQLiteDatabase db, int userId, int reservationId, String title, String message, String createdAt) {
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("reservation_id", reservationId);
        values.put("title", title);
        values.put("message", message);
        values.put("created_at", createdAt);
        db.insert(NotificationDao.TABLE_NAME, null, values);
    }

    private void insertReservation(
            SQLiteDatabase db, int userId, String reservationDate,
            String reservationTime, int partySize, String status,
            String createdAt, String lastModified
    ) {
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("reservation_date", reservationDate);
        values.put("reservation_time", reservationTime);
        values.put("party_size", partySize);
        values.put("status", status);
        values.put("created_at", createdAt);
        values.put("last_modified", lastModified);
        db.insert(ReservationDao.TABLE_NAME, null, values);
    }

    private void insertNotificationPreference(SQLiteDatabase db, int userId, String notificationType, int enabled) {
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("notification_type", notificationType);
        values.put("enabled", enabled);
        db.insert(NotificationPreferenceDao.TABLE_NAME, null, values);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
        db.execSQL("DROP TABLE IF EXISTS " + MenuItemDao.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ReservationDao.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NotificationDao.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NotificationPreferenceDao.TABLE_NAME);
        onCreate(db);
    }
}
