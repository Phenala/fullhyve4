package com.ux7.fullhyve.services.Models;


public class TeamMessage extends Message{
    public User sender;

    public TeamMessage(int id, String message, String timestamp, boolean seen, boolean sent) {
        super(id, message, timestamp, seen, sent);
    }
}
