package com.ux7.fullhyve.services.Storage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ux7.fullhyve.services.Models.ContactSet;
import com.ux7.fullhyve.services.Models.Identity;
import com.ux7.fullhyve.services.Models.NotificationSet;
import com.ux7.fullhyve.services.Models.ProjectSet;
import com.ux7.fullhyve.services.Models.TeamSet;

public class AppData extends Application {
    private static AppData sInstance;
    private static AppData.Cache cache; // Generic your-application handler
    public static String userToken = "sample";

    public static AppData getInstance() {
        if(sInstance==null){
            sInstance = new AppData();
        }
        return sInstance;
    }

    public Cache getCache() {
//        if(cache == null){
//            cache = new Cache(this.getSharedPreferences( "PREFS_PRIVATE", Context.MODE_PRIVATE ));
//        }
        return cache;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sInstance.initializeInstance();
        Log.e("Obj",this.toString());
    }
    protected void initializeInstance() {
        // do all your initialization here
        cache = new Cache(
                this.getSharedPreferences( "PREFS_PRIVATE", Context.MODE_PRIVATE ) );
    }


    /** This is a stand-in for some application-specific session handler. */
    public class Cache {
        private String token = "";
        private Identity identity = null;
        private final NotificationSet notifications = new NotificationSet();
        private final ContactSet contacts = new ContactSet();
        private final TeamSet teams = new TeamSet();
        private final ProjectSet projects = new ProjectSet();


        SharedPreferences sp;
        Cache(SharedPreferences sp) {
            this.sp = sp;
        }

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
