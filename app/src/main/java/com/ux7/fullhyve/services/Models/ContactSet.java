package com.ux7.fullhyve.services.Models;

import android.util.Log;

import com.ux7.fullhyve.services.Utility.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

// contacts
public class ContactSet implements Serializable{
    private HashMap<Integer, SendMessage> sendQueue;
    private HashMap<Integer, Contact> contacts;

    public ContactSet(){
        this.sendQueue = new HashMap<>();
        this.contacts = new HashMap<>();
    }

    public Contact getFriend(int friendId){
        if(contacts.containsKey(friendId)){
            return contacts.get(friendId);
        }
        return null;
    }

    public void addFriends(ArrayList<Contact> contacts){
        for (Contact contact:contacts){
            this.contacts.put(contact.getId(),contact);
        }
    }

    public void removeFriend(int userId){
        if(contacts.containsKey(userId)){
            contacts.remove(userId);
        }
    }

    public List<Contact> getFriends(int offfset, int limit){
        //Util.sliceArray(new ArrayList<>(contacts.values()),offfset, limit);
        return Util.sliceArray(new ArrayList<>(contacts.values()),offfset, limit);
    }

    public List<Contact> searchContacts(String name, int offset, int limit){
        ArrayList<Contact> friendsR = new ArrayList<>();

        if(name == null && (name = name.trim()) == ""){
            return null;
        } else {
            String[] searchName = name.split(" ");
            Log.e("Search Investigation", "split " + searchName.length);
            Collection<Contact> friends = contacts.values();
            Log.e("Search Investigation", "contacts" + contacts.values().size());

            for (Contact friend : friends) {
                if (searchName.length == 1) {
                    if (friend.getFirstName().contains(searchName[0]) || friend.getLastName().contains(searchName[0])) {
                        friendsR.add(friend);
                    }
                } else {
                    if (friend.getFirstName().contains(searchName[0]) && friend.getLastName().contains(searchName[1])) {
                        friendsR.add(friend);
                    }
                }
            }
        }

        return friendsR;
    }

    public void resetContacts(){
        contacts.clear();
    }

    public int addSendMessage(SendMessage sendMessage){
        sendQueue.put(sendMessage.getTempId(),sendMessage);
        return sendMessage.getTempId();
    }

    public void removeSendMessage(int tempId){
        sendQueue.remove(tempId);
    }






    // newly added
    //-----------------------------------------------------------------------------------------


}




