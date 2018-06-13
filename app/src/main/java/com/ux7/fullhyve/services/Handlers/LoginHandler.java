package com.ux7.fullhyve.services.Handlers;


import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonElement;
import com.ux7.fullhyve.services.Models.Identity;
import com.ux7.fullhyve.services.Utility.RequestFormat;
import com.ux7.fullhyve.services.Utility.ResponseFormat;
import com.ux7.fullhyve.services.Utility.ResponseListener;
import com.github.nkzawa.socketio.client.Ack;
import com.ux7.fullhyve.ui.activities.LoginView;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginHandler extends Handler {

    public void userConnected(final Activity activity, final Runnable runnable){
        JsonElement req = RequestFormat.createRequestObj(null, "userConnected");

        socket.emit("userConnected", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }

    public void signin(String userName, String password, final Activity activity, final LoginView.LoginRunnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("userName",userName);
        args.put("password",password);

        JSONObject req = RequestFormat.createRequestObj("signin",args);

        Log.e("Sent Request", "true");

        socket.emit("signin", req, new Ack() {
            @Override
            public void call(Object... args) {
                Log.e("Responded", "true");
                if(generalHandler(args)==200){
                    Log.e("Responded", "true");
                    final ResponseFormat.SignInR statusR = gson.fromJson(args[0].toString(), ResponseFormat.SignInR.class);
                    cache.setToken(statusR.data.token);

                    runnable.loginSuccess = true;

                    activity.runOnUiThread(runnable);
                } else if (generalHandler(args) == 401) {

                    runnable.loginSuccess = false;

                    activity.runOnUiThread(runnable);

                }
            }
        });
    }

    public void signup(String firstName, String lastName, String email, String userName, String password, final Activity activity, final Runnable runnable){
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
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }


    public void signout(final Activity activity, final Runnable runnable){
        JsonElement req = RequestFormat.createRequestObj(null, "signout");

        socket.emit("signout", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    //final ResponseFormat.StatusR statusR = gson.fromJson(args[0].toString(), ResponseFormat.StatusR.class);
                    cache.setToken(null);
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }



    public void getProfile(final Activity activity, final Runnable runnable){
        Identity identity = cache.getIdentity();

        if(identity != null){
            activity.runOnUiThread(runnable);
        } else{
            JsonElement req = RequestFormat.createRequestObj(null, "getProfile");
            //Log.e("GSOn", req);
            socket.emit("getProfile", req, new Ack() {
                @Override
                public void call(Object... args) {
                    if(generalHandler(args)==200){
                        final ResponseFormat.GetProfileR statusR = gson.fromJson(args[0].toString(), ResponseFormat.GetProfileR.class);
                        cache.setIdentity(statusR.data);

                        activity.runOnUiThread(runnable);
                    }
                }
            });
        }

    }


    public void editProfile(final Identity identity, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("firstName", identity.getFirstName());
        args.put("lastName",identity.getLastName());
        args.put("email",identity.getEmail());
        args.put("image",identity.getImage());
        args.put("skills",identity.getSkills());
        args.put("description",identity.getDescription());
        args.put("title", identity.getTitle());

        JsonElement req = RequestFormat.createRequestObj(args, "editProfile");
        Log.e("GSON1",req.toString());

        socket.emit("editProfile", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    identity.setId(cache.getIdentity().getId());
                    cache.setIdentity(identity);

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }

    public void addFriend(int friendId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("friendId",friendId);

        JSONObject req = RequestFormat.createRequestObj("addFriend",args);

        socket.emit("addFriend", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }

    public void replyFriendRequest(final int requestId, final boolean accepted, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("requestId", requestId);
        args.put("accepted", accepted);

        JSONObject req = RequestFormat.createRequestObj("replyFriendRequest",args);

        socket.emit("replyFriendRequest", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }
    public void unfriend(final int friendId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("friendId", friendId);

        JSONObject req = RequestFormat.createRequestObj("unfriend",args);

        socket.emit("unfriend", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }
    public void getNotifications(final String lastNotificationTimestamp, final int offset, final int limit, final Activity activity, final Runnable runnable){
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

                    Log.e("Notification","Success");

                    if(notificationsR!=null){
                        Log.e("Notif size", "" + notificationsR.data.notifications.size());
                        cache.getNotifications().add(notificationsR.data.notifications);
                    }
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }
}
