package com.ux7.fullhyve.services.Models;

import com.ux7.fullhyve.services.Utility.Util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Contact extends User{
    boolean online;
    String lastOnline;
    int unseenMessages;
    ArrayList<Message> messages = new ArrayList<>();

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(String lastOnline) {
        this.lastOnline = lastOnline;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }

    public void setUnseenMessages(int unseenMessages) {
        this.unseenMessages = unseenMessages;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public boolean removeMessage(int id){
        for (int i = 0; i < messages.size(); i++) {
            if(messages.get(i).getId() == id){
                return messages.remove(i) != null;
            }
        }
        return false;
    }

    public boolean addMessages(List<Message> messages){
        return messages.addAll(0,messages);
    }

    public ArrayList<Message> getMessages(int offset, int limit){
        return Util.sliceArray((ArrayList<Message>) getMessages(), offset, limit);
    }

    public void changeFriendOnlineStatus(boolean onlineStatus, String lastOnline){
        this.online = onlineStatus;
        this.lastOnline = lastOnline;
    }
}
