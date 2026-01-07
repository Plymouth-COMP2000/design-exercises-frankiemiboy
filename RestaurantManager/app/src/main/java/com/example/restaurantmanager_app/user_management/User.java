package com.example.restaurantmanager_app.user_management;


// Model class for User Structure
public class User {

    private String username;
    private String password;   // used only for comparison
    private String firstname;
    private String lastname;
    private String email;
    private String contact;
    private String usertype;   // "staff" or "student"

    public User(
            String username, String password, String firstname, String lastname,
            String email, String contact, String usertype
    )
    {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.contact = contact;
        this.usertype = usertype;
    }

    // Required empty constructor for Gson
    public User() {}

    // Getters only (no setters needed for auth)
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getUsertype() { return usertype; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getContact() { return contact; }
}
