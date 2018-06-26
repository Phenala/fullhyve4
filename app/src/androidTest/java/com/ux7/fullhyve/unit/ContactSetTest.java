package com.ux7.fullhyve.unit;

import android.util.Log;

import com.ux7.fullhyve.services.Models.Contact;
import com.ux7.fullhyve.services.Models.ContactSet;
import com.ux7.fullhyve.services.Models.SendMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class,ContactSet.class})
public class ContactSetTest {

    private ContactSet mContactSet;

//    @Mock
//    HashMap<Integer, SendMessage> SendQueue;
//    @Mock
//    private HashMap<Integer, Contact> mContacts;

    @Before
    public void setupContactSet(){
        MockitoAnnotations.initMocks(this);

        mContactSet = new ContactSet();
        PowerMockito.mockStatic(Log.class);
    }

    @Test
    public void getFriend_Test(){
        ArrayList<Contact> contacts = new ArrayList<>();
        Contact jon = new Contact();
        int Id = jon.getId();
        contacts.add(jon);
        mContactSet.addFriends(contacts);
        assertEquals(jon,mContactSet.getFriend(Id));

    }

    @Test
    public void getFriends_Test() {
        ArrayList<Contact> contacts = new ArrayList<>();

        Contact jon = new Contact();
        Contact marry = new Contact();

        contacts.add(jon);
        contacts.add(marry);

        mContactSet.addFriends(contacts);
        assertEquals(contacts,mContactSet.getFriends(0,2));
        }

    @Test
    public void searchContacts_Test() {

        ArrayList<Contact> contacts = new ArrayList<>();
        Contact jon = new Contact();
        String fN = "Rob";
        String lN = "Get";
        jon.setFirstName(fN);
        jon.setLastName(lN);
        contacts.add(jon);
        mContactSet.addFriends(contacts);
        assertEquals(contacts,mContactSet.searchContacts( fN,0,1));


    }

    @Test
    public void resetContacts_Test() {

        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact());
        contacts.add(new Contact());
        contacts.add(new Contact());
        mContactSet.addFriends(contacts);
        mContactSet.resetContacts();
        assertEquals(null,mContactSet.getFriends(0,4));


    }

    @Test
    public void addSendMessage_Test() {
        SendMessage sendMessage = new SendMessage(1,"");
        int tempId = mContactSet.addSendMessage(sendMessage);
        HashMap <Integer, SendMessage> SendQueue = Whitebox.getInternalState(mContactSet,"sendQueue");
       assertEquals(sendMessage,SendQueue.get(tempId));

    }

   @Test
    public void removeSendMessage_Test() {

        SendMessage sendMessage = new SendMessage(1,"");
        int tempId = mContactSet.addSendMessage(sendMessage);
        mContactSet.removeSendMessage(tempId);
        HashMap <Integer, SendMessage> SendQueue = Whitebox.getInternalState(mContactSet,"sendQueue");
        assertEquals(null,SendQueue.get(tempId));

    }


    }


