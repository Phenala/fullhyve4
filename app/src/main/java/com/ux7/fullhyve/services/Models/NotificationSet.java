package com.ux7.fullhyve.services.Models;

import java.io.Serializable;
import java.util.ArrayList;

// notifications
public class NotificationSet implements Serializable{
    public static final ArrayList<Notification> notifications = new ArrayList<>();

    public void add(ArrayList<Notification> notificationsR){
        notifications.addAll(0,notificationsR);
    }

}
