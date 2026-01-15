package com.example.restaurantmanager_app.data.reservation;// In your other Java file, e.g., ReservationRepository.java

import android.content.Context;
import android.util.Log;

import com.example.restaurantmanager_app.data.notification.NotificationDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// This Class will act as the Business Layer under the DAO Design Pattern
public class ReservationService {

    private ReservationDao reservationDao;
    private Context context;


    public ReservationService(Context context) {
        this.context = context;
        this.reservationDao = new ReservationDao(context);
    }

    public long createNewReservation(String username, String date, String time, int partySize) {
        // Business Logic: Generate timestamps
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return reservationDao.createReservation(username, date, time, partySize, now, now);
    }

    public List<Reservation> getAllReservations() {
        return reservationDao.getAllReservations();
    }

    public List<Reservation> getUserReservations(String username) {
        return reservationDao.getUserReservations(username);
    }

    public boolean cancelExistingReservation(int reservationId) {
        // Find out who owns the reservation
        Reservation reservation = reservationDao.getReservationById(reservationId);
        String username = reservation.getUsername();

        // Cancel the reservation
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        int rowsAffected = reservationDao.cancelReservation(reservationId, now);

        if (rowsAffected > 0)
        {
            // Send a notification to the user
            NotificationDao notificationDao = new NotificationDao(context);
            String title = "Reservation Cancelled";
            String message =    "Your reservation for " + reservation.getReservation_date() +
                                " at " + reservation.getReservation_time() + " has been cancelled.";
            notificationDao.createNotification(username, reservationId, title, message);
            return true;
        }
        return false;
    }

    public boolean updateExistingReservation(int reservationId, String newDate, String newTime, int newPartySize) {
        // Find owner of reservation
        Reservation reservation = reservationDao.getReservationById(reservationId);
        String username = reservation.getUsername();

        // Update the reservation
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        int rowsAffected = reservationDao.updateReservation(reservationId, newDate, newTime, newPartySize, "confirmed", now);

        if (rowsAffected > 0) {
            NotificationDao notificationDao = new NotificationDao(context);
            String title = "Reservation Updated";
            String message =    "Your reservation for " + reservation.getReservation_date() +
                                " at " + reservation.getReservation_time() + " has been updated.";
            notificationDao.createNotification(username, reservationId, title, message);
        }
        Log.d("ReservationService", "Rows affected: " + rowsAffected);
        return rowsAffected > 0;
    }

    public boolean deleteExistingReservation(int reservationId) {
        int rowsAffected = reservationDao.deleteReservation(reservationId);
        return rowsAffected > 0;
    }

    public boolean deleteAllUserReservations(String username) {
        return reservationDao.deleteAllUserReservations(username);
    }
}
