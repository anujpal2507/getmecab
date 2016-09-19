package com.getmecab.customerapp.database;

/**
 * Created by anuj "email : anujpal2507@gmail.com" on 20/9/16.
 */
public class User {
    private String name;
    private String email;
    private String mobileNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
