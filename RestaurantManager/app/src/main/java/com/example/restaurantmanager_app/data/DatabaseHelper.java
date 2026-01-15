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
    private static final int DB_VERSION = 7; // Incremented version

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

    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }



    private void insertDummyMenuItems(SQLiteDatabase db) {
        insertMenuItem(db, "Margherita Pizza", "Classic cheese and tomato pizza", 8.99, "prototype_image", 1);
        insertMenuItem(db, "Spaghetti Carbonara", "Creamy pasta with bacon and parmesan", 10.50, "prototype_image", 1);
        insertMenuItem(db, "Caesar Salad", "Romaine lettuce with Caesar dressing", 6.75, "prototype_image", 1);
    }

    private void insertDummyNotifications(SQLiteDatabase db) {
        // Corrected dummy notifications
        insertNotification(db, "user1", 1, "Reservation Confirmation", "Your reservation has been confirmed!", "2023-08-25 10:30:00");
        insertNotification(db, "user2", 2, "New Menu Item", "We've added a new item to our menu!", "2023-08-29 11:45:00");
        insertNotification(db, "user3", 3, "Reservation Cancellation", "Your reservation has been cancelled.", "2023-08-27 12:05:00");
        insertNotification(db, "hill_banks", 4, "Reservation Changed", "We've added a new item to our menu", "2023-08-27 12:05:00");
        // --- Confirmed Future Reservations ---
        // ID 8
        insertNotification(db, "linus_torvalds", 8, "Reservation Confirmed", "Your table for 6 people is confirmed for April 10th.", "2026-01-13 11:35:00");

        // ID 13
        insertNotification(db, "linus_torvalds", 13, "Reservation Reminder", "Don't forget your dinner reservation coming up on May 22nd.", "2026-05-20 09:00:00");

        // ID 15
        insertNotification(db, "linus_torvalds", 15, "New Menu Launch", "We'll be serving our new summer menu during your visit on June 5th!", "2026-05-25 14:00:00");


        // --- Cancelled Reservations ---
        // ID 16
        insertNotification(db, "linus_torvalds", 16, "Reservation Cancelled", "Your booking for Valentine's Day (ID #16) has been cancelled.", "2026-01-10 16:05:00");

        // ID 17
        insertNotification(db, "linus_torvalds", 17, "Refund Processed", "The deposit for cancelled reservation #17 has been refunded to your card.", "2026-01-12 12:00:00");


        // --- Past / Expired Reservations ---
        // ID 18
        insertNotification(db, "linus_torvalds", 18, "Rate Your Visit", "Thanks for dining with us last November! How was the service?", "2025-11-16 09:00:00");

        // ID 19
        insertNotification(db, "linus_torvalds", 19, "We Miss You", "It's been a while since your visit in December. Book again soon!", "2026-01-05 10:00:00");


        // --- More Future Reservations ---
        // ID 20
        insertNotification(db, "linus_torvalds", 20, "Reservation Update", "We have noted your request for a high chair for reservation #20.", "2026-01-15 08:30:00");

        // ID 21
        insertNotification(db, "linus_torvalds", 21, "Reservation Confirmed", "Your table for 2 is all set for July 20th.", "2026-06-15 11:20:00");

        // ID 22
        insertNotification(db, "linus_torvalds", 22, "Chef's Special", "Pre-order the Chef's Special for your upcoming visit (ID #22).", "2026-07-01 17:45:00");
    }

    private void insertDummyNotificationPreferences(SQLiteDatabase db) {
        // Corrected dummy notification preferences
        insertNotificationPreference(db, "user1", "Cancelled Reservation", 1);
        insertNotificationPreference(db, "user2", "New Reservation", 1);
        insertNotificationPreference(db, "user3", "Reservation Confirmation", 0);
        insertNotificationPreference(db, "user3", "New Menu Item", 0);
        insertNotificationPreference(db, "user2", "Cancelled Reservation", 0);
    }

    private void insertDummyReservations(SQLiteDatabase db) {
        insertReservation(db, "user1", "2023-08-25", "18:30", 4, "confirmed", "2023-08-25 10:30:00", "2023-08-25 10:30:00");
        insertReservation(db, "user2", "2023-08-26", "19:00", 2, "confirmed", "2023-08-26 11:45:00", "2023-08-26 11:45:00");
        insertReservation(db, "user3", "2023-08-29", "20:15", 6, "confirmed", "2023-08-29 12:05:00", "2023-08-29 12:05:00");
        insertReservation(db, "hill_banks", "2026-01-12", "12:00", 2, "confirmed", "2023-08-29 12:05:00", "2023-08-29 12:05:00");
        insertReservation(db, "ada_lovelace", "2026-02-14", "19:00", 2, "confirmed", "2026-01-10 09:30:00", "2026-01-10 09:30:00");
        insertReservation(db, "grace_hopper", "2026-02-15", "12:30", 4, "confirmed", "2026-01-11 14:15:00", "2026-01-11 14:15:00");
        insertReservation(db, "katherine_johnson", "2026-02-20", "18:00", 3, "confirmed", "2026-01-12 10:00:00", "2026-01-12 10:00:00");
        insertReservation(db, "linus_torvalds", "2026-03-01", "20:00", 6, "confirmed", "2026-01-15 16:45:00", "2026-01-15 16:45:00");
        insertReservation(db, "tim_berners_lee", "2026-03-05", "13:00", 2, "confirmed", "2026-01-18 11:20:00", "2026-01-18 11:20:00");
        insertReservation(db, "ada_lovelace", "2026-03-10", "19:30", 2, "confirmed", "2026-01-20 08:00:00", "2026-01-20 08:00:00");
        insertReservation(db, "grace_hopper", "2026-03-12", "12:00", 5, "confirmed", "2026-01-22 13:10:00", "2026-01-22 13:10:00");
        insertReservation(db, "katherine_johnson", "2026-03-15", "18:30", 4, "confirmed", "2026-01-25 15:50:00", "2026-01-25 15:50:00");
        insertReservation(db, "linus_torvalds", "2026-03-20", "20:30", 8, "confirmed", "2026-01-28 17:00:00", "2026-01-28 17:00:00");
        insertReservation(db, "tim_berners_lee", "2026-03-25", "13:30", 3, "confirmed", "2026-01-30 12:45:00", "2026-01-30 12:45:00");

        // Past reservations (Expired)
        insertReservation(db, "linus_torvalds", "2025-11-15", "19:00", 4, "expired", "2025-10-01 10:00:00", "2025-11-16 10:00:00");
        insertReservation(db, "linus_torvalds", "2025-12-24", "13:00", 8, "expired", "2025-12-01 08:45:00", "2025-12-25 09:00:00");

        // Future reservations (Confirmed)
        insertReservation(db, "linus_torvalds", "2026-04-10", "20:00", 6, "confirmed", "2026-01-13 11:30:00", "2026-01-13 11:30:00");
        insertReservation(db, "linus_torvalds", "2026-05-22", "18:30", 2, "confirmed", "2026-01-14 09:15:00", "2026-01-14 09:15:00");
        insertReservation(db, "linus_torvalds", "2026-06-05", "19:00", 5, "confirmed", "2026-01-15 14:20:00", "2026-01-15 14:20:00");

        // Cancelled reservations (Mixed dates)
        insertReservation(db, "linus_torvalds", "2026-02-14", "21:00", 2, "cancelled", "2026-01-05 15:20:00", "2026-01-10 16:00:00");
        insertReservation(db, "linus_torvalds", "2026-03-01", "12:00", 10, "cancelled", "2026-01-08 10:00:00", "2026-01-12 11:30:00");
        insertReservation(db, "linus_torvalds", "2026-07-20", "20:30", 4, "cancelled", "2026-01-15 08:00:00", "2026-01-16 09:45:00");
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
    private void insertNotification(SQLiteDatabase db, String username, int reservationId, String title, String message, String createdAt) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("reservation_id", reservationId);
        values.put("title", title);
        values.put("message", message);
        values.put("created_at", createdAt);
        db.insert(NotificationDao.TABLE_NAME, null, values);
    }

    private void insertReservation(
            SQLiteDatabase db, String username, String reservationDate,
            String reservationTime, int partySize, String status,
            String createdAt, String lastModified
    ) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("reservation_date", reservationDate);
        values.put("reservation_time", reservationTime);
        values.put("party_size", partySize);
        values.put("status", status);
        values.put("created_at", createdAt);
        values.put("last_modified", lastModified);
        db.insert(ReservationDao.TABLE_NAME, null, values);
    }

    private void insertNotificationPreference(SQLiteDatabase db, String username, String notificationType, int enabled) {
        ContentValues values = new ContentValues();
        values.put("username", username);
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
