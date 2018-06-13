package com.ux7.fullhyve.services.Models;

import com.ux7.fullhyve.services.Utility.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

// contacts
public class ContactSet implements Serializable{
    private final ArrayList<SendMessage> sendQueue = new ArrayList<>();
    private final HashMap<Integer, Contact> chats = new HashMap<>();
    private final HashMap<Integer, User> users = new HashMap<>();

    public void ContactSet(ArrayList<SendMessage> sendQueue, Contact[] chats, User[] users){
        this.addSendMessage(sendQueue);
        this.addChats(chats);
        this.addUsers(users);
    }

    public ArrayList<Contact> getFriends(int offfset, int limit){
        return Util.sliceArray((ArrayList<Contact>)chats.values(),offfset, limit);
    }

    public ArrayList<Contact> searchFriends(String name, int offset, int limit){
        ArrayList<Contact> friendsR = new ArrayList<>();

        if(name == null && (name = name.trim()) == ""){
            return null;
        } else {
            String[] searchName = name.split(" ");
            Collection<Contact> friends = chats.values();

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

        return Util.sliceArray(friendsR,offset,limit);
    }




    public ArrayList<SendMessage> getSendQueue(int offset, int limit){
        return (ArrayList<SendMessage>) sendQueue.subList(offset, (sendQueue.size() > offset+limit)?offset+limit:sendQueue.size());
    }

    public ArrayList<User> getUsers(int offset, int limit){
        ArrayList<User> users = (ArrayList<User>) this.users.values();
        return (ArrayList<User>) users.subList(offset, (users.size() > offset+limit)?offset+limit:users.size());
    }

    public boolean addSendMessage(ArrayList<SendMessage> sendMessages){
        return sendQueue.addAll(sendMessages);
    }

    public boolean removeSendMessage(int tempId){
        for (int i = 0; i < sendQueue.size(); i++) {
            if(sendQueue.get(i).tempId == tempId){
                return sendQueue.remove(i) != null;
            }
        }
        return false;
    }

    public void addChats(Contact[] contacts){
        for(Contact contact:contacts){
            this.chats.put(contact.getId(),contact);
        }
    }

    public boolean removeChat(int id){
        for (int i = 0; i < chats.size(); i++) {
            if(chats.get(i).getId() == id){
                return chats.remove(i) != null;
            }
        }
        return false;
    }

    public void addUsers(User[] users){
        for(User user:users){
            this.users.put(user.getId(),user);
        }
    }

    public boolean removeUser(int id){
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId() == id){
                return users.remove(i) != null;
            }
        }
        return false;
    }




    // newly added
    //-----------------------------------------------------------------------------------------

    public Contact getContact(int friendId){
        return chats.get(friendId);
    }
}

//interface IContactSet{
//    Contact getContact(int friendId);
//    boolean addToSendQueue(SendMessage message);
//    boolean removeFromSendQueue(int tempId);
//}
//
//interface IContact{
//    boolean addMessage(Message message);
//    boolean removeMessage(int messageId);       // if message exists in cache
//}
//
//interface IMessage{
//    void setMessage(String message);            // if message exists in cache
//    void setSeenTrue();
//}



