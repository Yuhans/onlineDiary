package com.onlineDiary.logic.beans;

import java.sql.Timestamp;

public class Message {

    private String sender;
    private String receiver;
    private String text;
    private Timestamp date;

    public Message(String sender, String receiver, String text, Timestamp date) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }


}
