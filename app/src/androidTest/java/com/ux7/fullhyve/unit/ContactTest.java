package com.ux7.fullhyve.unit;

import com.ux7.fullhyve.services.Models.Contact;
import com.ux7.fullhyve.services.Models.Message;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ContactTest {

    private Contact mContact;


    @Before
    public void setupIdentity(){

        mContact = new Contact();
    }

    @Test
    public void isOnline_Test() {

    mContact.changeFriendOnlineStatus(true, "");
    assertTrue(mContact.isOnline());
    }

    @Test
    public void getLastOnline_Test() {
        String str = "LastOnline";
        mContact.changeFriendOnlineStatus(true, str);
        assertEquals(str,mContact.getLastOnline());
    }

    @Test
    public void getUnseenMessages_Test() {

        mContact.setUnseenMessages(22);
        assertEquals(22,mContact.getUnseenMessages());
    }

    @Test
    public void getMessages_Test() {
        ArrayList<Message> messages = new ArrayList<>();
        Message message = Mockito.mock(Message.class);
        messages.add(message);
        mContact.setMessages(messages);
        assertEquals(messages,mContact.getMessages());
    }

    @Test
    public void removeMessages_Test() {
        ArrayList<Message> messages = new ArrayList<>();
        Message message = Mockito.mock(Message.class);
        messages.add(message);
        int id = message.getId();
        mContact.setMessages(messages);
        assertTrue(mContact.removeMessage(id));
        assertTrue(mContact.getMessages().isEmpty());
    }

    @Test
    public void addMessage_Test() {
        ArrayList<Message> messages = new ArrayList<>();
        Message message = Mockito.mock(Message.class);
        messages.add(message);
        mContact.setMessages(messages);
        assertEquals(mContact.getMessages(),messages);

    }

    @Test
    public void editMessage_Test() {
        ArrayList<Message> messages = new ArrayList<>();
        Message message = new Message(22,"notEdited","",true,true);

        messages.add(message);
        int id = message.getId();
        mContact.setMessages(messages);
        mContact.editMessage(id,"edited");
        assertEquals(message.getMessage(),"edited");
    }

    @Test
    public void setMessageSeen_Test() {
        ArrayList<Message> messages = new ArrayList<>();
        Message message = Mockito.mock(Message.class);
        messages.add(message);
        mContact.setMessages(messages);
        message.setSeen(true);
        mContact.setMessagesSeen(message.getId());
        assertFalse(message.isSeen());
    }



}