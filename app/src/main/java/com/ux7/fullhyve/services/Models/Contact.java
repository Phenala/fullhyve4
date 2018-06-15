package com.ux7.fullhyve.services.Models;

import com.ux7.fullhyve.services.Utility.Util;

import java.io.Serializable;
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


    // custom functions

    public boolean removeMessage(int messageId){
        for (int i = 0; i < messages.size(); i++) {
            if(messages.get(i).getId() == messageId){
                return messages.remove(i) != null;
            }
        }
        return false;
    }

    public boolean addMessages(ArrayList<Message> messages){
        return this.messages.addAll(messages);
    }

    public void addMessage(Message message){
        messages.add(0,message);
    }

    public void addReceivedMessage(ArrayList<Message> messages){
        this.messages.addAll(0,messages);
    }

    public List<Message> getMessages(int offset, int limit){
        if(offset < 0 || limit <= 0 && offset >= messages.size()){
            return null;
        }
        return messages.subList(offset, offset + limit> messages.size()?messages.size():offset+limit);
    }

    public void editMessage(int messageId, String newMessage){
        for(int i=0;i<messages.size();i++){
            if(messages.get(i).getId()==messageId){
                messages.get(i).setMessage(newMessage);
            }
        }
    }

    public void setMessagesSeen(int messageId){
        for(int i=0;i<messages.size();i++){
            if(!messages.get(i).isSent() && messages.get(i).getId()<=messageId){
                messages.get(i).setSeen(true);
            }
        }
    }

    public void changeFriendOnlineStatus(boolean onlineStatus, String lastOnline){
        this.online = onlineStatus;
        this.lastOnline = lastOnline;
    }
}
