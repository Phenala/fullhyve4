package com.ux7.fullhyve.services.Models;

import com.ux7.fullhyve.services.Utility.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// notifications
public class NotificationSet{
    public final ArrayList<Notification> notifications;

    public NotificationSet() {
        this.notifications = new ArrayList<>();
    }

    public void add(ArrayList<Notification> notificationsR){
        notifications.addAll(0,notificationsR);
    }

    public void load(ArrayList<Notification> notificationsR){
        notifications.addAll(notificationsR);
    }

    public List<Notification> getNotifications(int offset, int limit){
        return Util.sliceArray(notifications,offset,limit);
    }

}
