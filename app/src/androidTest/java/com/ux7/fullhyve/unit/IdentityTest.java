package com.ux7.fullhyve.unit;

import com.ux7.fullhyve.services.Models.Identity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class IdentityTest {

    private Identity mIdentity;

//    @Mock
//    private HashMap<Integer, SendMessage> mSendQueue;
//    @Mock
//    private HashMap<Integer, Contact> mContacts;

    @Before
    public void setupIdentity(){
       // MockitoAnnotations.initMocks(this);

        mIdentity = new Identity();
    }

    @Test
    public void getUserName_Test() {
        String jon = "john1997";
        mIdentity.setUserName(jon);
        assertEquals(jon,mIdentity.getUserName());
    }

    @Test
    public void getEmail_Test() {
        String email = "john1997@email.com";
        mIdentity.setEmail(email);
        assertEquals(email,mIdentity.getEmail());
    }

    @Test
    public void toString_Test() {
        mIdentity.setFirstName("John");
        mIdentity.setLastName("Vause");
        mIdentity.setUserName("John1997");
        mIdentity.setEmail("John@mail.com");

        String identity = "Name: John Vause - Username: John1997 - Email: John@mail.com";
        assertEquals(identity,mIdentity.toString());
    }



}