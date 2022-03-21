package com.example.bank;

public class Transfer {
    private int senderID;
    private int receiverID;
    private float amount;
    private String dateTime;

    public Transfer(int senderID, int receiverID, float amount, String dateTime) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiver) {
        this.receiverID = receiverID;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}

