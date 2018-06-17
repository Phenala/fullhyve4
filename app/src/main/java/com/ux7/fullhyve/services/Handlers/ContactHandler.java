package com.ux7.fullhyve.services.Handlers;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonElement;
import com.ux7.fullhyve.services.Models.Contact;
import com.ux7.fullhyve.services.Models.Message;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.services.Utility.Converter;
import com.ux7.fullhyve.services.Models.SendMessage;
import com.ux7.fullhyve.services.Utility.Realtime;
import com.ux7.fullhyve.services.Utility.RequestFormat;
import com.ux7.fullhyve.services.Utility.ResponseFormat;
import com.ux7.fullhyve.services.Utility.ResponseListener;
import com.github.nkzawa.socketio.client.Ack;
import com.ux7.fullhyve.ui.data.ListContact;
import com.ux7.fullhyve.ui.data.ListMessage;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ContactHandler extends Handler {

    public void sendMessage(final int friendId, final String message, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("friendId",friendId);
        args.put("message", message);

        JSONObject req = RequestFormat.createRequestObj("sendMessage",args);

        final int tempId = AppData.getCache().getContacts().addSendMessage(new SendMessage(friendId,message));

        Realtime.socket.emit("sendMessage", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.MessageR messageR = gson.fromJson(args[0].toString(), ResponseFormat.MessageR.class);

                    // add message to cache
                    if(messageR!=null && messageR.data != null){
                        if(AppData.getCache().getContacts().getFriend(friendId) != null){
                            AppData.getCache().getContacts().getFriend(friendId).addMessage(messageR.data);
                            AppData.getCache().getContacts().getFriend(friendId).setLastMessage(messageR.data);
                        }
                        AppData.getCache().getContacts().removeSendMessage(tempId);
                    }
                    activity.runOnUiThread(runnable);

                }
            }
        });
    }

    public void editMessage(final int friendId, final int messageId, final String newMessage, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("messageId", messageId);
        args.put("newMessage", newMessage);

        JSONObject req = RequestFormat.createRequestObj("editMessage",args);

        Realtime.socket.emit("editMessage", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){

                    if(AppData.getCache().getContacts().getFriend(friendId)!=null){
                        AppData.getCache().getContacts().getFriend(friendId).editMessage(messageId,newMessage);
                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }
    public void forwardMessage(final int[] friendIds, final int messageId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("friendIds", friendIds);
        args.put("messageId", messageId);

        JsonElement req = RequestFormat.createRequestObj(args,"forwardMessage");

        Realtime.socket.emit("forwardMessage", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    // add the messages to the cache
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }
    public void deleteMessage(final int friendId, final int messageId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("messageId", messageId);

        JSONObject req = RequestFormat.createRequestObj("deleteMessage",args);

        Realtime.socket.emit("deleteMessage", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    AppData.getCache().getContacts().getFriend(friendId).removeMessage(messageId);
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }
    public void updateMessageSeen(final int friendId, final int lastMessageId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("lastMessageId", lastMessageId);

        JSONObject req = RequestFormat.createRequestObj("updateMessageSeen",args);

        Realtime.socket.emit("updateMessageSeen", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    AppData.getCache().getContacts().getFriend(friendId).setMessagesSeen(lastMessageId);
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }
    public void getFriendLastSeenTime(final int friendId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("friendId", friendId);

        JSONObject req = RequestFormat.createRequestObj("getFriendLastSeenTime",args);

        Realtime.socket.emit("getFriendLastSeenTime", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.GetLastOnlineR lastOnline = gson.fromJson(args[0].toString(), ResponseFormat.GetLastOnlineR.class);

                    if(lastOnline!=null && lastOnline.data!=null){
                        AppData.getCache().getContacts().getFriend(friendId).changeFriendOnlineStatus(lastOnline.data.online,lastOnline.data.timestamp);
                    } else{
                        Contact friend = AppData.getCache().getContacts().getFriend(friendId);

                        lastOnline.data.online = friend.isOnline();
                        lastOnline.data.timestamp = friend.getLastOnline();
                    }
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }

    public void getMessages(final int friendId, int offset, int limit, final List<ListMessage> listMessages, final Activity activity, final Runnable runnable){
        final ResponseFormat.GetMessagesR messagesR;

//        final ArrayList<Message> messages = AppData.getCache().getContacts().getContact(friendId).getMessages(offset, limit);

        Contact contact = AppData.getCache().getContacts().getFriend(friendId);
        List<Message> messages = null;
        Log.e("messages", "Requested");

        if(contact!=null){
            messages = contact.getMessages(offset, limit);
        }

        if(messages != null && messages.size()>0 && false){
            Log.e("Cached messages", messages.size()  + "");
            listMessages.clear();
            listMessages.addAll(Converter.portMessageToListMessage(messages));
            activity.runOnUiThread(runnable);
        } else {
            HashMap<String, Object> args = new HashMap<>();
            args.put("friendId", friendId);
            args.put("offset", offset);
            args.put("limit", limit);

            JSONObject req = RequestFormat.createRequestObj("getMessages", args);

            Realtime.socket.emit("getMessages", req, new Ack() {
                @Override
                public void call(Object... args) {

                    if (generalHandler(args) == 200) {
                        ResponseFormat.GetMessagesR messagesR = gson.fromJson(args[0].toString(), ResponseFormat.GetMessagesR.class);

//                        if(messagesR != null && messagesR.data.done){
//
//                            AppData.getCache().getContacts().getContact(friendId).addMessages(messagesR.data.messages);
//
//                        }

                        listMessages.clear();
                        listMessages.addAll(Converter.portMessageToListMessage(messagesR.data.messages));

                        if (messagesR != null && messagesR.data.messages != null) {
                            Log.e("Messages", "Received");
                            //AppData.getCache().getContacts().getContact(friendId).addMessages(messagesR.data.messages);
                            if (AppData.getCache().getContacts().getFriend(friendId) != null) {
                                AppData.getCache().getContacts().getFriend(friendId).addMessages((ArrayList<Message>) messagesR.data.messages);
                            }

                        }

                        activity.runOnUiThread(runnable);
                    }
                }
            });
        }
    }

    public void getFriends(int offset, int limit, final List<ListContact> listContacts, final Activity activity, final Runnable runnable){
        final ResponseFormat.GetFriends friendsR = new ResponseFormat().new GetFriends();

        List<Contact> friends = AppData.getCache().getContacts().getFriends(offset, limit);

        if(friends != null && friends.size() > 0){
            Log.e("Server","Not called");
            friendsR.friends = friends;

            listContacts.clear();
            listContacts.addAll(Converter.portContactToListContact(friends));
            activity.runOnUiThread(runnable);
        } else{
            HashMap<String, Object> args = new HashMap<>();
            args.put("offset", offset);
            args.put("limit", limit);

            JSONObject req = RequestFormat.createRequestObj("getFriends",args);

            Realtime.socket.emit("getFriends", req, new Ack() {
                @Override
                public void call(Object... args) {
                    if(generalHandler(args)==200){
                        final ResponseFormat.GetFriendsR friendsR = gson.fromJson(args[0].toString(), ResponseFormat.GetFriendsR.class);

                        if(friendsR!=null && friendsR.data.friends != null){
                            AppData.getCache().getContacts().addFriends((ArrayList<Contact>) friendsR.data.friends);
                        }
                        listContacts.clear();
                        listContacts.addAll(Converter.portContactToListContact(friendsR.data.friends));

                        activity.runOnUiThread(runnable);
                    }
                }
            });
        }


    }

    public void getFriendsFromServer(int offset, int limit, final List<ListContact> listContacts, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("offset", offset);
        args.put("limit", limit);

        JSONObject req = RequestFormat.createRequestObj("getFriends",args);

        Realtime.socket.emit("getFriends", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.GetFriendsR friendsR = gson.fromJson(args[0].toString(), ResponseFormat.GetFriendsR.class);

                    if(friendsR!=null){
                        //AppData.getCache().contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = messageR.data.message;

                        Log.e("Friends number",friendsR.data.friends.size()+"");
                    if(friendsR!=null && friendsR.data.friends != null){
                        AppData.getCache().getContacts().addFriends((ArrayList<Contact>) friendsR.data.friends);

                        listContacts.clear();
                        listContacts.addAll(Converter.portContactToListContact(friendsR.data.friends));
                    }

                    activity.runOnUiThread(runnable);

                    }
                }
            }
        });
    }

    public void searchUsers(final String name, final int offset, final int limit, final List<ListContact> listContacts, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("name", name);
        args.put("offset", offset);
        args.put("limit", limit);

        JSONObject req = RequestFormat.createRequestObj("searchUsers",args);

        Realtime.socket.emit("searchUsers", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    Log.e("Respon searchusers", args[0].toString());
                    final ResponseFormat.SearchUsersR searchUsersR = gson.fromJson(args[0].toString(), ResponseFormat.SearchUsersR.class);
                    List<Contact> friends = AppData.getCache().getContacts().searchContacts(name, offset, limit);

                    //searchUsersR.data.friends = new ArrayList<>();
                    //searchUsersR.data.friends.addAll(friends);

                    listContacts.clear();
                    listContacts.addAll(Converter.portUserToListContact(searchUsersR.data.users));

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }

}
