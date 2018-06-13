package com.ux7.fullhyve.services.Handlers;

import android.app.Activity;
import android.util.Log;

import com.ux7.fullhyve.services.Models.Contact;
import com.ux7.fullhyve.services.Models.Message;
import com.ux7.fullhyve.services.Utility.Converter;
import com.ux7.fullhyve.services.Utility.RequestFormat;
import com.ux7.fullhyve.services.Utility.ResponseFormat;
import com.ux7.fullhyve.services.Utility.ResponseListener;
import com.github.nkzawa.socketio.client.Ack;
import com.ux7.fullhyve.ui.data.ListContact;
import com.ux7.fullhyve.ui.data.ListMessage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactHandler extends Handler {

    public void sendMessage(final int friendId, final String message, final Activity activity, final Runnable runnable){
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
                    activity.runOnUiThread(runnable);

                }
            }
        });
    }

    public void editMessage(final int messageId, final String newMessage, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("messageId", messageId);
        args.put("newMessage", newMessage);

        JSONObject req = RequestFormat.createRequestObj("editMessage",args);

        socket.emit("editMessage", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }
    public void forwardMessage(final int[] friendIds, final int messageId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("friendIds", friendIds);
        args.put("messageId", messageId);

        JSONObject req = RequestFormat.createRequestObj("forwardMessage",args);

        socket.emit("forwardMessage", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }
    public void deleteMessage(final int messageId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("messageId", messageId);

        JSONObject req = RequestFormat.createRequestObj("deleteMessage",args);

        socket.emit("deleteMessage", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }
    public void updateMessageSeen(final int contactId, final int lastMessageId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("contactId", contactId);
        args.put("lastMessageId", lastMessageId);

        JSONObject req = RequestFormat.createRequestObj("updateMessageSeen",args);

        socket.emit("updateMessageSeen", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }
    public void getFriendLastSeenTime(final int friendId, final Activity activity, final Runnable runnable){
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
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }

    public void getMessages(final int friendId, int offset, int limit, final List<ListMessage> listMessages, final Activity activity, final Runnable runnable){
        final ResponseFormat.GetMessagesR messagesR;

//        final ArrayList<Message> messages = cache.getContacts().getContact(friendId).getMessages(offset, limit);

//        if(messages != null){
//            // call semaphore here
//        } else{
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

//                        if(messagesR != null && messagesR.data.done){
//
//                            cache.getContacts().getContact(friendId).addMessages(messagesR.data.messages);
//                        }

                        listMessages.clear();
                        listMessages.addAll(Converter.portMessageToListMessage(messagesR.data.messages));

                        activity.runOnUiThread(runnable);
                    }
                }
            });
//        }
    }

    public void getFriends(int offset, int limit, final Activity activity, final Runnable runnable){
        final ResponseFormat.GetFriends friendsR = new ResponseFormat().new GetFriends();

        ArrayList<Contact> friends = cache.getContacts().getFriends(offset, limit);

        if(friends==null){
            friendsR.done = true;
        }

        friendsR.friends = friends;
    }

    public void getFriendsFromServer(int offset, int limit, final List<ListContact> listContacts, final Activity activity, final Runnable runnable){
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
                        listContacts.clear();
                        listContacts.addAll(Converter.portContactToListContact(friendsR.data.friends));

                        Log.e("Friends number",friendsR.data.friends.size()+"");
                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }

    public void searchUsers(final String name, final int offset, final int limit, final Activity activity, final Runnable runnable){
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

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }

}
