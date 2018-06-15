package com.ux7.fullhyve.services.Models;


public class SendMessage{
    private static int tempIds = 0;
    private int tempId;
    private int receiverId;
    private String message;

    public SendMessage(int receiverId, String message) {
        this.tempId = tempIds;
        this.receiverId = receiverId;
        this.message = message;

        tempIds++;
    }

    public static int getTempIds() {
        return tempIds;
    }

    public static void setTempIds(int tempIds) {
        SendMessage.tempIds = tempIds;
    }

    public int getTempId() {
        return tempId;
    }

    public void setTempId(int tempId) {
        this.tempId = tempId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
