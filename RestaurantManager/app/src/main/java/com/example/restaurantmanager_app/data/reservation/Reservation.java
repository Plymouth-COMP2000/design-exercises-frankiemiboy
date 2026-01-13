package com.example.restaurantmanager_app.data.reservation;

import android.os.Parcel;
import android.os.Parcelable;

public class Reservation implements Parcelable {
    private int reservationId;
    private String username;
    private String reservation_date;
    private String reservation_time;
    private int party_size;
    private String status;
    private String created_at;
    private String last_modified;

    // Transient fields
    private transient String firstName;
    private transient String lastName;
    private transient String phoneNumber;



    public Reservation(
            int reservationId, String username, String reservation_date,
            String reservation_time, int party_size, String status,
            String created_at, String last_modified
    ) {
        this.reservationId = reservationId;
        this.username = username;
        this.reservation_date = reservation_date;
        this.reservation_time = reservation_time;
        this.party_size = party_size;
        this.status = status;
        this.created_at = created_at;
        this.last_modified = last_modified;
    }

    // Write the object data to a Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(reservationId);
        dest.writeString(username);
        dest.writeString(reservation_date);
        dest.writeString(reservation_time);
        dest.writeInt(party_size);
        dest.writeString(status);
        dest.writeString(created_at);
        dest.writeString(last_modified);
    }

    // Constructor used to turn Parcel into Reservation object
    public Reservation(Parcel in) {
        reservationId = in.readInt();
        username = in.readString();
        reservation_date = in.readString();
        reservation_time = in.readString();
        party_size = in.readInt();
        status = in.readString();
        created_at = in.readString();
        last_modified = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    // Getter Methods
    public int getReservationId() { return reservationId; }
    public String getUsername() { return username; }
    public String getReservation_date() { return reservation_date; }
    public String getReservation_time() { return reservation_time; }
    public int getParty_size() { return party_size; }
    public String getStatus() { return status; }
    public String getCreated_at() { return created_at; }
    public String getLast_modified() { return last_modified; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNumber() { return phoneNumber; }

    // Setter Methods
    public void setTransientDetails(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
