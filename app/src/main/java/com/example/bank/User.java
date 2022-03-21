package com.example.bank;

public class User {
    private int id;
    private String username;
    private String email;
    private float currentBalance;

    public User(String username, String email, float currentBalance) {
        this.username = username;
        this.email = email;
        this.currentBalance = currentBalance;
    }

    public User() {
    }

    public User(int id, String username, String email, float currentBalance) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.currentBalance = currentBalance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(float currentBalance) {
        this.currentBalance = currentBalance;
    }

}
