package com.ux7.fullhyve.services.Utility;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Handlers.LoginHandler;
import com.ux7.fullhyve.services.Models.Contact;
import com.ux7.fullhyve.services.Storage.AppData;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ux7.fullhyve.ui.activities.ContactView;
import com.ux7.fullhyve.ui.activities.HomeView;
import com.ux7.fullhyve.ui.fragments.ContactsListFragment;

import java.net.URISyntaxException;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Realtime {
    public static Realtime realtime = new Realtime();

    private static Emitter.Listener onFriendDisconnected;
    private static Emitter.Listener onFriendConnected;
    private static Emitter.Listener onReceiveMessage;
    private static Emitter.Listener onEditMessage;
    private static Emitter.Listener onDeleteMessage;
    private static Emitter.Listener onUpdateMessageSeen;

    private static Emitter.Listener onAuthenticationError;


    public static Socket socket = null;
    private static AppHandler appHandler = AppHandler.getInstance();

    private static GsonBuilder gsonBuilder;
    private static Gson gson;

    public static final String URL = "http://192.168.43.117:8000/";


    public Realtime(){
        // initialize json parsers
        gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        gson = gsonBuilder.create();

        getSocket();
        Log.e("App handler", "Created"+appHandler);
    }

    public static Realtime getRealtime(){
        return realtime;
    }

    public static String getBearerToken(){
        return "token=Bearer "+ AppData.getCache().getToken();
    }

    public static Socket getSocket() {
        // create socket if not created already, then return instance
        Log.e("Realtime token",AppData.getCache().getToken()==null?"No token":AppData.getCache().getToken());
        try {
//            if(AppData.getCache().getToken() != null){
//                IO.Options options = new IO.Options();
//                options.query = getBearerToken();
//                socket = IO.socket(URL, options);
//            } else{
//
//            }


            socket = IO.socket(URL + "chat");


            initializeResponseHandlers();

            // connect socket
            connectSocket();

        } catch (URISyntaxException e) {
            Log.e("Connected","false");
        }

        return socket;
    }

    public static void connectSocket(){
        socket.connect();

        Log.e("Socket Connect", "attempt to connect");
        if(AppData.getCache().getToken() != null){
            LoginHandler loginHandler = new LoginHandler();

//            loginHandler.userConnected(new ResponseListener() {
//                @Override
//                public void call(Object... data) {
//                    Log.e("status", "Connected now online!!");
//                }
//            });
        }
    }

    public static Socket getInstance(){
        if(socket==null){
            return getSocket();
        }
        return socket;
    }


    public static void detachResponseHandlers(){
        socket.off("receiveMessageR", onReceiveMessage);
        socket.off("editMessageR", onEditMessage);
        socket.off("deleteMessageR", onDeleteMessage);
        socket.off("updateMessageSeenR", onDeleteMessage);
        socket.off("friendDisconnected", onFriendDisconnected);
    }


    private static void initializeResponseHandlers(){
        onFriendDisconnected = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if(args.length>0){
                    final ResponseFormat.DisconnectedUserR userR = gson.fromJson(args[0].toString(), ResponseFormat.DisconnectedUserR.class);
                    AppData.getCache().getContacts().getFriend(userR.data.userId).changeFriendOnlineStatus(false, userR.data.timestamp);
                }
            }
        };

        onFriendConnected = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if(args.length>0){
                    final ResponseFormat.DisconnectedUserR userR = gson.fromJson(args[0].toString(), ResponseFormat.DisconnectedUserR.class);
                    AppData.getCache().getContacts().getFriend(userR.data.userId).changeFriendOnlineStatus(true, userR.data.timestamp);
                }
            }
        };

        onReceiveMessage = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if(args.length>0){
                    Log.e("Recvvvv", args[0].toString());
                    ResponseFormat.IORecieveMessage messages = gson.fromJson(args[0].toString(), ResponseFormat.IORecieveMessage.class);
                    Log.e("Recieve Emission", messages.toString());

                    Log.e("Recieve Emission", "recieve message emit");
                    if(messages!=null){
                            //Contact friend = AppData.getCache().contacts.getContact(message.senderId);
//                            if(friend != null){
//                                friend.addMessages(message.messages);
//                            }
                        if (ContactView.hoistedActivity != null)
                            ContactView.hoistedActivity.runOnUiThread(ContactView.hoistedActivity.update);
                        else if (ContactsListFragment.hoistedFragment != null)
                            ContactsListFragment.hoistedFragment.getActivity().runOnUiThread(ContactsListFragment.hoistedFragment.update);
                        else {
                            NotificationCompat.Builder nb = new NotificationCompat.Builder(HomeView.hoistedActivity);
                            String frnd = AppData.getCache().getContacts().getFriend(messages.friendId).getName();
                            nb.setContentTitle("New Message").setContentText(frnd + ": " + messages.getMessage());
                            NotificationManager nm = (NotificationManager) HomeView.hoistedActivity.getSystemService(NOTIFICATION_SERVICE);
                            nb.setSmallIcon(R.mipmap.logo_foreground);
                            nb.setAutoCancel(true);
                            nb.setTicker(frnd + ": " + messages.getMessage());
                            nb.setVibrate(new long[]{4, 1, 4, 1});
                            Intent intent = new Intent(HomeView.hoistedActivity, HomeView.class);
                            intent.putExtra("target", "contacts");
                            nb.setContentIntent(PendingIntent.getActivity(HomeView.hoistedActivity, 15222, intent, PendingIntent.FLAG_UPDATE_CURRENT));
                            nm.notify(15222, nb.build());
                        }
                    }
                }
            }
        };

        onEditMessage = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if(args.length>0){
                    //AppData.userToken = (String)args[0];
                }
            }
        };

        onDeleteMessage = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if(args.length>0){
                    //AppData.userToken = (String)args[0];
                }
            }
        };

        onUpdateMessageSeen = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if(args.length>0){
                    //AppData.userToken = (String)args[0];
                }
            }
        };

        onAuthenticationError = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if(args.length>0){
                    Log.e("Authentication","Error");
                    //AppData.userToken = (String)args[0];
                }
            }
        };


        socket.on("receiveMessageR", onReceiveMessage);
        socket.on("editMessageR", onEditMessage);
        socket.on("deleteMessageR", onDeleteMessage);
        socket.on("updateMessageSeenR", onDeleteMessage);
        socket.on("friendDisconnected", onFriendDisconnected);
        socket.on("friendConnected", onFriendConnected);
        socket.on("authenticationError",onAuthenticationError);
    }

}
