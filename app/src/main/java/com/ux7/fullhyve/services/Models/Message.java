package com.ux7.fullhyve.services.Models;

import java.io.Serializable;
import java.util.Date;

public class Message{
    private int id;
    private String message;
    private String timestamp;
    private boolean seen;
    private boolean sent;


    public Message(int id, String message, String timestamp, boolean seen, boolean sent) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.seen = seen;
        this.sent = sent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }


}
