package com.ux7.fullhyve.services.Handlers;


import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.services.Utility.Realtime;
import com.ux7.fullhyve.services.Utility.ResponseFormat;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Handler {
    protected static AppData appData;
    protected static AppData.Cache cache;

    private static GsonBuilder gsonBuilder;
    static Gson gson;

    Socket socket = Realtime.getSocket();

    Handler(){
        gsonBuilder = new GsonBuilder();
        //gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gson = gsonBuilder.create();


        appData = AppData.getInstance();
        cache = appData.getCache();
    }

    public int generalHandler(Object... args){
        if(args.length>0){
            ResponseFormat responseFormat = gson.fromJson(args[0].toString(), ResponseFormat.class);

            //Log.e("Response: ",responseFormat.toString());

            switch (responseFormat.code){
                // successful
                case 200:
                    return 200;

                // invalid data
                case 400:
                    break;

                // unauthenticated
                case 401:
                    break;

                // unauthorized
                case 403:
                    break;

                // server error
                case 500:
                    break;

                default:
                    return 200;
            }
        }

        return 0;
    }
}
