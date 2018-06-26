package com.ux7.fullhyve.unit;

import android.app.Activity;

import com.google.gson.JsonArray;
import com.ux7.fullhyve.services.Handlers.LoginHandler;
import com.ux7.fullhyve.services.Models.Identity;
import com.ux7.fullhyve.services.Utility.Realtime;
import com.ux7.fullhyve.services.Utility.RequestFormat;
import com.ux7.fullhyve.ui.activities.LoginView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Created by Admin on 6/18/2018.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonArray.class,RequestFormat.class, Realtime.class})
public class LoginHandlerTest extends HandlerTest{
    @Mock
    private RequestFormat mRequestFormat;
    @Mock
    private Realtime mRealtime;

    @InjectMocks
    private LoginHandler mLoginHandler = new LoginHandler();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Realtime.class);
        PowerMockito.mockStatic(JsonArray.class);
        PowerMockito.mockStatic(RequestFormat.class);
    }

    @Test
    public void UserConnected_CreatesRequestFormatAndSends(){
        when(mRequestFormat.createRequestObj(any(), anyString())).thenReturn(null);
//        doAnswer(mRealtime,new Answer<>())
        Activity activity = mock(Activity.class);
        Runnable runnable = mock(Runnable.class);
//
        mLoginHandler.userConnected(activity,runnable);

        verify(mRequestFormat).createRequestObj(any(),anyString());
//        verifyNew(,times(1)).createRequestObj(any(),anyString());
//        verify(mRealtime).socket.emit(anyString(), any(JsonElement.class), Matchers.any(Ack.class));

//        verify(activity).runOnUiThread(runnable);
    }

    @Test
    public void SignIn_CheckSuccessfulSignIn(){

        mLoginHandler.signin(anyString(),anyString(), any(Activity.class),  any(LoginView.LoginRunnable.class));
    }

    @Test
    public void signOut_Test(){
        mLoginHandler.signout(any(Activity.class),any(Runnable.class));
    }

    @Test
    public void GetProfile_Test(){
        mLoginHandler.getProfile(any(Activity.class),any(Runnable.class));
        verify(mRequestFormat).createRequestObj(null,"getProfile");
    }

    @Test
    public void GetUserProfile_Test(){
//        mLoginHandler.getProfile(anyInt(),any(Enclosure.class),any(Activity.class),any(Runnable.class));
    }

    @Test
    public void EditProfile(){
        Identity identity = new Identity();
        identity.setUserName("f");
        identity.setEmail("rt@d");
        identity.setFirstName("fr");
        identity.setLastName("rt");
        identity.setImage("/rgg");
        identity.setSkills(new String[]{"work"});
        identity.setDescription("desc");
        identity.setTitle("title");
        Runnable run = new Runnable() {
            @Override
            public void run() {

            }
        };
        mLoginHandler.editProfile(identity,new Activity(),run);
    }

    @Test
    public void AddFriend(){
        int id = 1;
        HashMap<String, Object> args = new HashMap<>();
        args.put("friendId",id);

        Runnable run = new Runnable() {
            @Override
            public void run() {

            }
        };
        mLoginHandler.addFriend(id, new Activity(), run);

        verify(mRequestFormat).createRequestObj("addFriend",args);
    }
}