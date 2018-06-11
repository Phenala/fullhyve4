package com.ux7.fullhyve.services.Handlers;

import android.util.Log;

import com.ux7.fullhyve.services.Models.Contact;
import com.ux7.fullhyve.services.Models.Message;
import com.ux7.fullhyve.services.Utility.RequestFormat;
import com.ux7.fullhyve.services.Utility.ResponseFormat;
import com.ux7.fullhyve.services.Utility.ResponseListener;
import com.github.nkzawa.socketio.client.Ack;
import com.ux7.fullhyve.ui.data.ListMessage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ContactHandler extends Handler {

    public void userConnected(final int userId, final ResponseListener responseListener){
        HashMap<String, Object> args = new HashMap<>();
        args.put("id", userId);

        JSONObject req = RequestFormat.createRequestObj("userConnected",args);

        socket.emit("userConnected", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    responseListener.call(true);
                }
            }
        });
    }

    public void sendMessage(final int friendId, final String message, final Semaphore semaphore){
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
                        //Message msg = new Message(messageR.data.msgId, message,);
                        //cache.getContacts().getContact(friendId).addMessages(new )
                    }
//                    responseListener.call(messageR);

                    semaphore.release();

                }
            }
        });
    }

    public void getMessages(final int friendId, int offset, int limit, final List<ListMessage> messageList, final Semaphore semaphore){
        final ResponseFormat.GetMessagesR messagesR;

//        final ArrayList<Message> messages = cache.getContacts().getContact(friendId).getMessages(offset, limit);
//        if(messages != null){
//
//            messageList.addAll(portMessageToListMessage(messages));
//            // call semaphore here
//            semaphore.release();
//        } else{
            HashMap<String, Object> args = new HashMap<>();
            args.put("friendId",friendId);
            args.put("offset", offset);
            args.put("limit", limit);


            Log.e("Fondo", "oold");

            JSONObject req = RequestFormat.createRequestObj("getMessages",args);

            socket.emit("getMessages", req, new Ack() {
                @Override
                public void call(Object... args) {
                    Log.e("Fondo", "discreet");
                    if(generalHandler(args)==200){
                        ResponseFormat.GetMessagesR messagesR = gson.fromJson(args[0].toString(), ResponseFormat.GetMessagesR.class);

//                        if(messagesR != null && messagesR.data.done){
//                            cache.getContacts().getContact(friendId).addMessages(messagesR.data.messages);
//                        }

                        messageList.addAll(portMessageToListMessage(messagesR.data.messages));


                        // call semaphore here
                        semaphore.release();
                    }
                }
            });
//        }
    }

    public List<ListMessage> portMessageToListMessage(List<Message> messages) {

        List<ListMessage> uiMessages = new ArrayList<>();

        for (Message message : messages) {
            ListMessage lstmsg = new ListMessage();
            lstmsg.id = message.getId();
            lstmsg.message = message.getMessage();
            lstmsg.sent = message.isSent();
            lstmsg.time = message.getTimestamp();
            uiMessages.add(lstmsg);
        }

        return uiMessages;

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
