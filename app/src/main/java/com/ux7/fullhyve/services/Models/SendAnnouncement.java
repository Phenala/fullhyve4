package com.ux7.fullhyve.services.Models;


public class SendAnnouncement{
    private static int tempIds = 0;
    private int tempId;
    private int teamId;
    private String message;

    public SendAnnouncement(int teamId, String message) {
        this.teamId = teamId;
        this.message = message;
        this.tempId = tempIds;

        tempIds++;
    }

    public int getTempId(){
        return tempId;
    }
}
