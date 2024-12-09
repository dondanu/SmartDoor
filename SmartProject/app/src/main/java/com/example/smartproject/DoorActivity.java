package com.example.smartproject;

// Door Activity Model Class
public class DoorActivity {
    private String date;   // Door activity timestamp
    private String status; // Door status (Opened/Closed)

    // Empty Constructor (Firebase needs this)
    public DoorActivity() {
    }

    // Constructor
    public DoorActivity(String date, String status) {
        this.date = date;
        this.status = status;
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
