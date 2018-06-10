package com.ux7.fullhyve.services.Utility;

import android.app.Application;
import android.util.Log;

import com.ux7.fullhyve.services.Storage.AppData;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URISyntaxException;

public class Realtime extends Application {
    private static Emitter.Listener onFriendDisconnected;
    private static Emitter.Listener onFriendConnected;
    private static Emitter.Listener onReceiveMessage;
    private static Emitter.Listener onEditMessage;
    private static Emitter.Listener onDeleteMessage;
    private static Emitter.Listener onUpdateMessageSeen;


    private static Socket socket=null;
    private static AppData appData = AppData.getInstance();
    private static AppData.Cache cache = appData.getCache();

    private static GsonBuilder gsonBuilder;
    private static Gson gson;

    static String url = "http://192.168.43.117:8000/chat";


    public static Socket getSocket() {
        Log.e("Obj",appData.toString());

        // initialize json parsers
        gsonBuilder = new GsonBuilder();
        //gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gson = gsonBuilder.create();


        // create socket if not created already then return instance
        if(socket==null){
            try {
                IO.Options options = new IO.Options();
                //options.query = "token=Bearer "+ cache.getToken();
                socket = IO.socket( url,options);
                //Toast.makeText(this,"Connected",Toast.LENGTH_LONG).show();
                initializeResponseHandlers();

            } catch (URISyntaxException e) {
                //Toast.makeText(this,"Unable to connect",Toast.LENGTH_LONG).show();
                Log.e("Connected","false");
            }
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
                    cache.getContacts().getContact(userR.data.userId).changeFriendOnlineStatus(false, userR.data.timestamp);
                }
            }
        };

        onFriendConnected = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if(args.length>0){
                    final ResponseFormat.DisconnectedUserR userR = gson.fromJson(args[0].toString(), ResponseFormat.DisconnectedUserR.class);
                    cache.getContacts().getContact(userR.data.userId).changeFriendOnlineStatus(true, userR.data.timestamp);
                }
            }
        };

        onReceiveMessage = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if(args.length>0){
//                    ResponseFormat.ReceivedMessagesR messages = gson.fromJson(args[0].toString(), ResponseFormat.ReceivedMessagesR.class);
//
//                    if(messages!=null && messages.data.size() > 0){
//                        for(ResponseFormat.ReceivedMessages message:messages.data){
//                            Contact friend = cache.contacts.getContact(message.senderId);
//                            if(friend != null){
//                                friend.addMessages(message.messages);
//                            }
//                        }
//                    }
                }
            }
        };

        onEditMessage = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if(args.length>0){
                    AppData.userToken = (String)args[0];
                }
            }
        };

        onDeleteMessage = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if(args.length>0){
                    AppData.userToken = (String)args[0];
                }
            }
        };

        onUpdateMessageSeen = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if(args.length>0){
                    AppData.userToken = (String)args[0];
                }
            }
        };


        socket.on("receiveMessageR", onReceiveMessage);
        socket.on("editMessageR", onEditMessage);
        socket.on("deleteMessageR", onDeleteMessage);
        socket.on("updateMessageSeenR", onDeleteMessage);
        socket.on("friendDisconnected", onFriendDisconnected);
        socket.on("friendConnected", onFriendConnected);
    }

}
