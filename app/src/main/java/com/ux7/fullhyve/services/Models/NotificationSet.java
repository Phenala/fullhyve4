package com.ux7.fullhyve.services.Models;

import java.util.ArrayList;

// notifications
public class NotificationSet{
    public static final ArrayList<Notification> notifications = new ArrayList<>();

    public void add(ArrayList<Notification> notificationsR){
        notifications.addAll(0,notificationsR);
    }

}
