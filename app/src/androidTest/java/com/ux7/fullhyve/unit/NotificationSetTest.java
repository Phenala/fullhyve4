package com.ux7.fullhyve.unit;

import android.util.Log;

import com.ux7.fullhyve.services.Models.Notification;
import com.ux7.fullhyve.services.Models.NotificationSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Admin on 6/17/2018.
 */

/*
    Unit test for the implementations of NotificationSetTest
*/
@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class NotificationSetTest {
    private NotificationSet mNotificationSet;

    @Before
    public void setupNotificationSet(){
        MockitoAnnotations.initMocks(this);


        mNotificationSet = new NotificationSet();
        PowerMockito.mockStatic(Log.class);

    }

//    @Test
//    public void addNotifications_NotificationsAddedToNotifactionsArrayList(){
//        ArrayList<Notification> notifications = mock(ArrayList.class);
//        Mockito.when(notifications.size()).thenReturn(2);
//
//        Mockito.when(notifications.get(0)).thenReturn(any(Notification.class));
//        Mockito.when(notifications.get(1)).thenReturn(any(Notification.class));
//
//        mNotificationSet.add(notifications);
//        verify(mNotifications).addAll(0,notifications);
//    }


//    @Test
//    public void loadNotifications_NotificationsAddedToNotificationsArrList(){
////        ArrayList<Notification> notifications = mock(ArrayList.class);
////        Mockito.when(notifications.size()).thenReturn(1);
////        Mockito.when(notifications.get(0)).thenReturn(new Notification());
////        Mockito.when(notifications.get(1)).thenReturn(any(Notification.class));
//        List<Notification> spylist = spy(new ArrayList<Notification>());
//        spylist.add(new Notification());
//        spylist.add(new Notification());
//
////        verify(spylist).add(new Notification());
////        verify(spylist).add(new Notification());
//
//        assertEquals(2, spylist.size());
//        mNotificationSet.load((ArrayList<Notification>) spylist);
//        verify(mNotifications).addAll(spylist);
//    }

    @Test
    public void GetNotifications_Test(){
        ArrayList<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification());
        notifications.add(new Notification());
        notifications.add(new Notification());

        mNotificationSet.add(notifications);

        assertEquals(notifications,mNotificationSet.getNotifications(0,3));
        assertEquals(notifications,mNotificationSet.getNotifications(0,4));
        assertEquals(notifications,mNotificationSet.getNotifications(0,5));

    }

    @Test
    public void AddToNotificationList_Test(){
        ArrayList<Notification> notifications = new ArrayList<>();
        Notification notification = new Notification();
        notifications.add(notification);
        mNotificationSet.add(notifications);
        assertEquals(notification, mNotificationSet.notifications.get(0));
    }

}