package com.ux7.fullhyve.services.Models;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Admin on 6/18/2018.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({})
public class ContactSetTest {
    private ContactSet mContactSet;

    @Before
    public void setup(){
        mContactSet = new ContactSet();
    }

    @Test
    public void AddSendMessage_Test(){
        SendMessage sendMessage = new SendMessage(2,"");
        mContactSet.addSendMessage(sendMessage);
        HashMap<Integer, SendMessage> sendQueue = Whitebox.getInternalState(mContactSet,"sendQueue");

        assertEquals(sendMessage,sendQueue.get(sendMessage.getTempId()));
    }

}