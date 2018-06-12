package com.ux7.fullhyve.services.Handlers;


import android.util.Log;

import com.ux7.fullhyve.services.Models.Identity;
import com.ux7.fullhyve.services.Utility.RequestFormat;
import com.ux7.fullhyve.services.Utility.ResponseFormat;
import com.ux7.fullhyve.services.Utility.ResponseListener;
import com.github.nkzawa.socketio.client.Ack;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginHandler extends Handler {

    public LoginHandler(){
        super();
    }

    public void userConnected(final ResponseListener responseListener){
        JSONObject req = RequestFormat.createRequestObj("userConnected",null);

        socket.emit("userConnected", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    responseListener.call(true);
                }
            }
        });
    }

    public void signin(String userName, String password, final ResponseListener responseListener){
        HashMap<String, Object> args = new HashMap<>();
        args.put("userName",userName);
        args.put("password",password);

        JSONObject req = RequestFormat.createRequestObj("signin",args);

        socket.emit("signin", req, new Ack() {
            @Override
            public void call(Object... args) {
                Log.e("Responded", "true");
                if(generalHandler(args)==200){
                    Log.e("Responded", "true");
                    final ResponseFormat.SignInR statusR = gson.fromJson(args[0].toString(), ResponseFormat.SignInR.class);
                    cache.setToken(statusR.data.token);

                    responseListener.call(true);
                }
            }
        });
    }

    public void signup(String firstName, String lastName, String email, String userName, String password, final ResponseListener responseListener){
        HashMap<String, Object> args = new HashMap<>();
        args.put("firstName",firstName);
        args.put("lastName",lastName);
        args.put("email",email);
        args.put("userName",userName);
        args.put("password",password);

        JSONObject req = RequestFormat.createRequestObj("signup",args);

        socket.emit("signup", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    //final ResponseFormat.StatusR statusR = gson.fromJson(args[0].toString(), ResponseFormat.StatusR.class);
                    responseListener.call(true);
                }
            }
        });
    }


    public void signout(final ResponseListener responseListener){
        JSONObject req = RequestFormat.createRequestObj("signout",null);

        socket.emit("signout", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    //final ResponseFormat.StatusR statusR = gson.fromJson(args[0].toString(), ResponseFormat.StatusR.class);
                    cache.setToken(null);
                    responseListener.call(true);
                }
            }
        });
    }



    public void getProfile(final ResponseListener responseListener){
        Identity identity = cache.getIdentity();

        if(identity != null){
            responseListener.call(identity);
        } else{
            JSONObject req = RequestFormat.createRequestObj("getProfile",null);

            socket.emit("getProfile", req, new Ack() {
                @Override
                public void call(Object... args) {
                    if(generalHandler(args)==200){
                        final ResponseFormat.GetProfileR statusR = gson.fromJson(args[0].toString(), ResponseFormat.GetProfileR.class);
                        cache.setIdentity(statusR.data);

                        responseListener.call(statusR.data);
                    }
                }
            });
        }

    }


    public void editProfile(final Identity identity, final ResponseListener responseListener){
        HashMap<String, Object> args = new HashMap<>();
        args.put("firstName", identity.getFirstName());
        args.put("lastName",identity.getLastName());
        args.put("email",identity.getEmail());
        args.put("username",identity.getUserName());
        args.put("image",identity.getImage());
        args.put("skills",identity.getSkills());
        args.put("description",identity.getDescription());

        JSONObject req = RequestFormat.createRequestObj("editProfile",args);

        socket.emit("editProfile", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    identity.setId(cache.getIdentity().getId());
                    cache.setIdentity(identity);

                    responseListener.call(true);
                }
            }
        });
    }

    public void addFriend(int friendId, final ResponseListener responseListener){
        HashMap<String, Object> args = new HashMap<>();
        args.put("friendId",friendId);

        JSONObject req = RequestFormat.createRequestObj("addFriend",args);

        socket.emit("addFriend", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    responseListener.call(true);
                }
            }
        });
    }

    public void replyFriendRequest(final int requestId, final boolean accepted, final ResponseListener responseListener){
        HashMap<String, Object> args = new HashMap<>();
        args.put("requestId", requestId);
        args.put("accepted", accepted);

        JSONObject req = RequestFormat.createRequestObj("replyFriendRequest",args);

        socket.emit("replyFriendRequest", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    responseListener.call(true);
                }
            }
        });
    }
    public void unfriend(final int friendId/*contactId*/, final ResponseListener responseListener){
        HashMap<String, Object> args = new HashMap<>();
        args.put("friendId", friendId);


        JSONObject req = RequestFormat.createRequestObj("unfriend",args);

        socket.emit("unfriend", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){

                    responseListener.call(true);
                }
            }
        });
    }
    public void getNotifications(final String lastNotificationTimestamp, final int offset, final int limit, final ResponseListener responseListener){
        HashMap<String, Object> args = new HashMap<>();
        //args.put("lastNotificationTimestamp", lastNotificationTimestamp);
        args.put("offset", offset);
        args.put("limit", limit);


        JSONObject req = RequestFormat.createRequestObj("getNotifications",args);

        socket.emit("getNotifications", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.GetNotificationsR notificationsR = gson.fromJson(args[0].toString(), ResponseFormat.GetNotificationsR.class);

                    if(notificationsR!=null){
                        cache.getNotifications().add(notificationsR.data.notifications);
                    }
                    responseListener.call(notificationsR);
                }
            }
        });
    }
}
