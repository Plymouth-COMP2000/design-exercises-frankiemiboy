package com.example.restaurantmanager_app.data.reservation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurantmanager_app.data.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

// This Class handles the CRUD Operations for Reservations
public class ReservationDao {
    public static final String TABLE_NAME = "Reservation";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username TEXT NOT NULL," +
            "reservation_date TEXT NOT NULL," +
            "reservation_time TEXT NOT NULL," +
            "party_size INTEGER NOT NULL," +
            "status TEXT DEFAULT 'confirmed' NOT NULL," + // All reservations are considered confirmed when created
            "created_at TEXT NOT NULL," +
            "last_modified TEXT NOT NULL" +
            ")";

    private DatabaseHelper dbHelper;

    public ReservationDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // --------------- CREATE OPERATIONS -----------------
    // This method is used to create a new reservation in the database
    public long createReservation(
            String username, String date, String time,
            int partySize, String createdAt, String lastModified) {

        // 1. Get the writable database instance using your new DatabaseManager
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 2. Create a ContentValues object to hold the new row's data
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("reservation_date", date);
        values.put("reservation_time", time);
        values.put("party_size", partySize);
        values.put("status", "confirmed"); // Default status
        values.put("created_at", createdAt);
        values.put("last_modified", lastModified);


        // 3. Insert the new row into the reservations table
        // The `db.insert()` method returns the row ID of the newly inserted row,
        // or -1 if an error occurred.
        long newRowId = db.insert(ReservationDao.TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }


    // ---------- READ OPERATIONS --------------------
    // This is for staff members that will have access to all reservations
    public List<Reservation> getAllReservations() {

        List<Reservation> reservations = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve all reservations;
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String reservationDate = cursor.getString(cursor.getColumnIndexOrThrow("reservation_date"));
                String reservationTime = cursor.getString(cursor.getColumnIndexOrThrow("reservation_time"));
                int partySize = cursor.getInt(cursor.getColumnIndexOrThrow("party_size"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status")).toUpperCase();
                String createdAt = cursor.getString(cursor.getColumnIndexOrThrow("created_at"));
                String lastModified = cursor.getString(cursor.getColumnIndexOrThrow("last_modified"));

                Reservation reservation = new Reservation(id, username, reservationDate, reservationTime, partySize, status, createdAt, lastModified);
                reservations.add(reservation);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return reservations;
    }

    // This is for guests that will have access to only their reservations
    public List<Reservation> getUserReservations(String username) {
        List<Reservation> reservations = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve all reservations for a specific user;
        String query =  "SELECT * FROM " + TABLE_NAME + " " +
                        "WHERE username = ?" + " " +
                        "ORDER BY reservation_date DESC, reservation_time DESC";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String reservationUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String reservationDate = cursor.getString(cursor.getColumnIndexOrThrow("reservation_date"));
                String reservationTime = cursor.getString(cursor.getColumnIndexOrThrow("reservation_time"));
                int partySize = cursor.getInt(cursor.getColumnIndexOrThrow("party_size"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status")).toUpperCase();
                String createdAt = cursor.getString(cursor.getColumnIndexOrThrow("created_at"));
                String lastModified = cursor.getString(cursor.getColumnIndexOrThrow("last_modified"));

                Reservation reservation = new Reservation(id, reservationUsername, reservationDate, reservationTime, partySize, status, createdAt, lastModified);
                reservations.add(reservation);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return reservations;
    }

    // This method is used to get a reservation by its ID
    public Reservation getReservationById(int reservationId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve the reservation
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(reservationId)});

        if (cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String reservationDate = cursor.getString(cursor.getColumnIndexOrThrow("reservation_date"));
            String reservationTime = cursor.getString(cursor.getColumnIndexOrThrow("reservation_time"));
            int partySize = cursor.getInt(cursor.getColumnIndexOrThrow("party_size"));
            String status = cursor.getString(cursor.getColumnIndexOrThrow("status")).toUpperCase();
            String createdAt = cursor.getString(cursor.getColumnIndexOrThrow("created_at"));
            String lastModified = cursor.getString(cursor.getColumnIndexOrThrow("last_modified"));

            Reservation reservation = new Reservation(reservationId, username, reservationDate, reservationTime, partySize, status, createdAt, lastModified);
            cursor.close();
            return reservation;
        } else {
            cursor.close();
            return null; // Return null if no reservation is found
        }
    }


    // ------------------ UPDATE OPERATIONS -------------------
    // This method is used to update a reservation in the database
    public int updateReservation(
            int reservationId, String newDate, String newTime,
            int newPartySize, String status, String lastModified
    ) {
        // 1. Get the writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 2. Create ContentValues with the new values
        // We only update these values because these are the only values that are likely to change
        ContentValues values = new ContentValues();
        values.put("reservation_date", newDate);
        values.put("reservation_time", newTime);
        values.put("party_size", newPartySize);
        values.put("status", status);
        values.put("last_modified", lastModified);



        // 3. Define the 'where' part of the query
        // This specifies which row to update (the one with the matching reservation_id)
        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(reservationId) };

        // 4. Update the row
        // db.update() method returns the number of rows affected by update.
        int count = db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
        return count; // Returns 1 if successful, 0 if no row was found with that ID
    }

    // This method will change the reservation status to cancelled
    // Inside ReservationDao.java
    public int cancelReservation(int reservationId, String lastModified) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", "cancelled");
        values.put("last_modified", lastModified);

        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(reservationId) };

        int count = db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
        return count;
    }


    // -------------------- DELETE OPERATIONS ---------------------
    // This method is used to delete a reservation from the database
    public int deleteReservation(int reservationId) {
        // 1. Get the writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(reservationId)});
        db.close();
        return 1; // Returns 1 if successful, 0 if no row was found with that ID
    }
}
