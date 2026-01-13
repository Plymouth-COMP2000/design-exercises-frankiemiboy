package com.example.restaurantmanager_app.data.reservation;// In your other Java file, e.g., ReservationRepository.java

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// This Class will act as the Business Layer under the DAO Design Pattern
public class ReservationService {

    private ReservationDao reservationDao;

    public ReservationService(Context context) {
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

    public List<Reservation> getAllConfirmedReservations() {
        return reservationDao.getAllConfirmedReservations();
    }

    public List<Reservation> getAllNotConfirmedReservations() {
        return reservationDao.getAllNotConfirmedReservations();
    }

    public List<Reservation> getUserReservations(String username) {
        return reservationDao.getUserReservations(username);
    }

    public List<Reservation> getUserConfirmedReservations(String username) {
        return reservationDao.getUserConfirmedReservations(username);
    }

    public List<Reservation> getUserNotConfirmedReservations(String username) {
        return reservationDao.getUserNotConfirmedReservations(username);
    }



    public boolean cancelExistingReservation(int reservationId) {
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        int rowsAffected = reservationDao.cancelReservation(reservationId, now);
        return rowsAffected > 0;
    }

    public boolean updateExistingReservation(int reservationId, String newDate, String newTime, int newPartySize) {
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        int rowsAffected = reservationDao.updateReservation(reservationId, newDate, newTime, newPartySize, "confirmed", now);
        Log.d("ReservationService", "Rows affected: " + rowsAffected);
        return rowsAffected > 0;
    }

    public boolean deleteExistingReservation(int reservationId) {
        int rowsAffected = reservationDao.deleteReservation(reservationId);
        return rowsAffected > 0;
    }
}
