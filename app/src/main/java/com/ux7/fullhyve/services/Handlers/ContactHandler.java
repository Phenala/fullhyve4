package com.ux7.fullhyve.services.Handlers;

import com.ux7.fullhyve.services.Models.Contact;
import com.ux7.fullhyve.services.Models.Message;
import com.ux7.fullhyve.services.Utility.RequestFormat;
import com.ux7.fullhyve.services.Utility.ResponseFormat;
import com.ux7.fullhyve.services.Utility.ResponseListener;
import com.github.nkzawa.socketio.client.Ack;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactHandler extends Handler {

    public void sendMessage(final int friendId, final String message, final ResponseListener responseListener){
        HashMap<String, Object> args = new HashMap<>();
        args.put("friendId",friendId);
        args.put("message", message);

        JSONObject req = RequestFormat.createRequestObj("sendMessage",args);

        socket.emit("sendMessage", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.MessageR messageR = gson.fromJson(args[0].toString(), ResponseFormat.MessageR.class);

                    if(messageR!=null && messageR.data != null){
                        //Message msg = new Message(messageR.data.msgId, message, Calendar.getInstance().getTime());
                        //cache.getContacts().getContact(friendId).addMessages(new )
                    }
                    responseListener.call(messageR);

                }
            }
        });
    }

    public void editMessage(final int messageId, final String newMessage, final ResponseListener responseListener){
        HashMap<String, Object> args = new HashMap<>();
        args.put("messageId", messageId);
        args.put("newMessage", newMessage);

        JSONObject req = RequestFormat.createRequestObj("editMessage",args);

        socket.emit("editMessage", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){

                    responseListener.call(true);
                }
            }
        });
    }
    public void forwardMessage(final int[] friendIds, final int messageId, final ResponseListener responseListener){
        HashMap<String, Object> args = new HashMap<>();
        args.put("friendIds", friendIds);
        args.put("messageId", messageId);

        JSONObject req = RequestFormat.createRequestObj("forwardMessage",args);

        socket.emit("forwardMessage", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){

                    responseListener.call(true);
                }
            }
        });
    }
    public void deleteMessage(final int messageId, final ResponseListener responseListener){
        HashMap<String, Object> args = new HashMap<>();
        args.put("messageId", messageId);

        JSONObject req = RequestFormat.createRequestObj("deleteMessage",args);

        socket.emit("deleteMessage", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){

                    responseListener.call(true);
                }
            }
        });
    }
    public void updateMessageSeen(final int contactId, final int lastMessageId, final ResponseListener responseListener){
        HashMap<String, Object> args = new HashMap<>();
        args.put("contactId", contactId);
        args.put("lastMessageId", lastMessageId);

        JSONObject req = RequestFormat.createRequestObj("updateMessageSeen",args);

        socket.emit("updateMessageSeen", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    responseListener.call(true);
                }
            }
        });
    }
    public void getFriendLastSeenTime(final int friendId, final ResponseListener responseListener){
        HashMap<String, Object> args = new HashMap<>();
        args.put("friendId", friendId);

        JSONObject req = RequestFormat.createRequestObj("getFriendLastSeenTime",args);

        socket.emit("getFriendLastSeenTime", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.GetLastOnline lastOnline = gson.fromJson(args[0].toString(), ResponseFormat.GetLastOnline.class);

                    if(lastOnline!=null){
                        //cache.contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = messageR.data.message;
                    }
                    responseListener.call(lastOnline);
                }
            }
        });
    }

    public void getMessages(final int friendId, int offset, int limit){
        final ResponseFormat.GetMessagesR messagesR;

        ArrayList<Message> messages = cache.getContacts().getContact(friendId).getMessages(offset, limit);
        if(messages != null){
            // call semaphore here
        } else{
            HashMap<String, Object> args = new HashMap<>();
            args.put("friendId",friendId);
            args.put("offset", offset);
            args.put("limit", limit);

            JSONObject req = RequestFormat.createRequestObj("getMessages",args);

            socket.emit("getMessages", req, new Ack() {
                @Override
                public void call(Object... args) {
                    if(generalHandler(args)==200){
                        ResponseFormat.GetMessagesR messagesR = gson.fromJson(args[0].toString(), ResponseFormat.GetMessagesR.class);

                        if(messagesR != null && messagesR.data.done){
                            cache.getContacts().getContact(friendId).addMessages(messagesR.data.messages);
                        }

                        // call semaphore here
                    }
                }
            });
        }
    }

    public void getFriends(int offset, int limit, final ResponseListener responseListener){
        final ResponseFormat.GetFriends friendsR = new ResponseFormat().new GetFriends();

        ArrayList<Contact> friends = cache.getContacts().getFriends(offset, limit);

        if(friends==null){
            friendsR.done = true;
        }

        friendsR.friends = friends;

        responseListener.call(friendsR);
    }

    public void getFriendsFromServer(int offset, int limit, final ResponseListener responseListener){
        HashMap<String, Object> args = new HashMap<>();
        args.put("offset", offset);
        args.put("limit", limit);


        JSONObject req = RequestFormat.createRequestObj("getFriends",args);

        socket.emit("getFriends", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.GetFriendsR friendsR = gson.fromJson(args[0].toString(), ResponseFormat.GetFriendsR.class);

                    if(friendsR!=null){
                        //cache.contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = messageR.data.message;
                    }
                    responseListener.call(friendsR);
                }
            }
        });
    }

    public void searchUsers(final String name, final int offset, final int limit, final ResponseListener responseListener){
        HashMap<String, Object> args = new HashMap<>();
        args.put("name", name);
        args.put("offset", offset);
        args.put("limit", limit);


        JSONObject req = RequestFormat.createRequestObj("searchUsers",args);

        socket.emit("searchUsers", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.SearchUsersR searchUsersR = gson.fromJson(args[0].toString(), ResponseFormat.SearchUsersR.class);
                    ArrayList<Contact> friends = cache.getContacts().searchFriends(name, offset, limit);

                    searchUsersR.data.friends.addAll(friends);

                    responseListener.call(searchUsersR);
                }
            }
        });
    }

}
