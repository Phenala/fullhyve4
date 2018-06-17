package com.ux7.fullhyve.services.Storage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Handlers.Handler;
import com.ux7.fullhyve.services.Models.ContactSet;
import com.ux7.fullhyve.services.Models.Identity;
import com.ux7.fullhyve.services.Models.NotificationSet;
import com.ux7.fullhyve.services.Models.ProjectSet;
import com.ux7.fullhyve.services.Models.TeamSet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;

public class AppData extends Application {
    private static String CACHE_FILE_NAME = "fullhyveCache.json";
    private static Gson gson;

    private static AppData sInstance;
    private static AppData.Cache cache; // Generic your-application handler

    public static AppData getInstance() {
        return sInstance;
    }

    public static Cache getCache() {
        if (cache == null) {
            AppData appData = AppData.getInstance();
            appData.initializeInstance();
        }
        return cache;
    }

    public static void resetCache(){
        AppData appData  = AppData.getInstance();
        appData.initializeInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        gson = new GsonBuilder().create();

        sInstance = this;
        sInstance.initializeInstance();
    }

    public void initializeInstance() {
        // do all your initialization here
        cache = new Cache(
                /*this.getSharedPreferences( "PREFS_PRIVATE", Context.MODE_PRIVATE )*/ );
    }



    // read and save cache
    // ================================================================

    public void saveCache(Context context){
        String cacheStr = gson.toJson(cache);
        Log.e("Cache",cacheStr);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(AppData.CACHE_FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(cacheStr);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "Saving cache failed");
        }
    }

    public void emptyCache(Context context){
        String cacheStr = gson.toJson(new Cache());
        Log.e("Cache",cacheStr);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(AppData.CACHE_FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(cacheStr);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "Saving cache failed");
        }
    }

    public void readCache(Context context){
        String cacheStr = "";

        try {
            InputStream inputStream = context.openFileInput(AppData.CACHE_FILE_NAME);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                cacheStr = stringBuilder.toString().trim();
            }
        }
        catch (FileNotFoundException e) {
            return;
        } catch (IOException e) {
            Log.e("Cache", "Unable to read file");
            return;
        }
        Log.e("Read cache",cacheStr);
        AppData.cache = gson.fromJson(cacheStr, Cache.class);
    }


    //===========================================================


    /** This is a stand-in for some application-specific session handler. */
    public class Cache{
        private String token = null;
        private Identity identity = null;
        private final NotificationSet notifications = new NotificationSet();
        private final ContactSet contacts = new ContactSet();
        private final TeamSet teams = new TeamSet();
        private final ProjectSet projects = new ProjectSet();

        Cache(){

        }
//        SharedPreferences sp;
//        Cache(/*SharedPreferences sp*/) {
//            this.sp = sp;
//        }

        public String getToken() {
            return token;
        }

        public Identity getIdentity() {
            return identity;
        }

        public NotificationSet getNotifications() {
            return notifications;
        }

        public ContactSet getContacts() {
            return contacts;
        }

        public TeamSet getTeams() {
            return teams;
        }

        public ProjectSet getProjects() {
            return projects;
        }

        public void setToken(String token){
            this.token = token;
        }

        public void setIdentity(Identity identity){
            this.identity = identity;
        }
    }

}
