package com.ux7.fullhyve.services.Models;

import java.util.ArrayList;

// notifications
public class NotificationSet{
    public final ArrayList<Notification> notifications = new ArrayList<>();

    public void add(ArrayList<Notification> notifications){
        this.notifications.addAll(0,notifications);
    }


}
