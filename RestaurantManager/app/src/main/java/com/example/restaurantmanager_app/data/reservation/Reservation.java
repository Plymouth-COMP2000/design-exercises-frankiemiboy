package com.example.restaurantmanager_app.data.reservation;

public class Reservation {
    private int reservationId;
    private int userId;
    private String reservation_date;
    private String reservation_time;
    private int party_size;
    private String status;
    private String created_at;
    private String last_modified;

    public Reservation(int reservationId, int userId, String reservation_date,
                       String reservation_time, int party_size, String status,
                       String created_at, String last_modified) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.reservation_date = reservation_date;
        this.reservation_time = reservation_time;
        this.party_size = party_size;
        this.status = status;
        this.created_at = created_at;
        this.last_modified = last_modified;
    }

    public int getReservationId() { return reservationId; }
    public int getUserId() { return userId; }
    public String getReservation_date() { return reservation_date; }
    public String getReservation_time() { return reservation_time; }
    public int getParty_size() { return party_size; }
    public String getStatus() { return status; }
    public String getCreated_at() { return created_at; }
    public String getLast_modified() { return last_modified; }

}
