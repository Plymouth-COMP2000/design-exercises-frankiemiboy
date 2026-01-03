package com.example.restaurantmanager_app.data.reservation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurantmanager_app.data.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ReservationDao {
    public static final String TABLE_NAME = "Reservation";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user_id INTEGER NOT NULL," +
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

    public List<Reservation> getAllReservations() {

        List<Reservation> reservations = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve all reservations;
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                String reservationDate = cursor.getString(cursor.getColumnIndexOrThrow("reservation_date"));
                String reservationTime = cursor.getString(cursor.getColumnIndexOrThrow("reservation_time"));
                int partySize = cursor.getInt(cursor.getColumnIndexOrThrow("party_size"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status")).toUpperCase();
                String createdAt = cursor.getString(cursor.getColumnIndexOrThrow("created_at"));
                String lastModified = cursor.getString(cursor.getColumnIndexOrThrow("last_modified"));

                Reservation reservation = new Reservation(id, userId, reservationDate, reservationTime, partySize, status, createdAt, lastModified);
                reservations.add(reservation);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return reservations;
    }

    // Perhaps user only wants to see Reservations that are not cancelled;
    public List<Reservation> getAllConfirmedReservations() {

        List<Reservation> reservations = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve all reservations;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE status != 'cancelled'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                String reservationDate = cursor.getString(cursor.getColumnIndexOrThrow("reservation_date"));
                String reservationTime = cursor.getString(cursor.getColumnIndexOrThrow("reservation_time"));
                int partySize = cursor.getInt(cursor.getColumnIndexOrThrow("party_size"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status")).toUpperCase();
                String createdAt = cursor.getString(cursor.getColumnIndexOrThrow("created_at"));
                String lastModified = cursor.getString(cursor.getColumnIndexOrThrow("last_modified"));

                Reservation reservation = new Reservation(id, userId, reservationDate, reservationTime, partySize, status, createdAt, lastModified);
                reservations.add(reservation);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return reservations;
    }

}
