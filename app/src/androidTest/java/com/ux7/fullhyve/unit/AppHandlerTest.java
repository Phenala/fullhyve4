package com.ux7.fullhyve.unit;

import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Handlers.ContactHandler;
import com.ux7.fullhyve.services.Handlers.ImageUploadHandler;
import com.ux7.fullhyve.services.Handlers.LoginHandler;
import com.ux7.fullhyve.services.Handlers.ProfileHandler;
import com.ux7.fullhyve.services.Handlers.ProjectHandler;
import com.ux7.fullhyve.services.Handlers.RegisterHandler;
import com.ux7.fullhyve.services.Handlers.TeamHandler;
import com.ux7.fullhyve.services.Handlers.UserSelectHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by Admin on 6/22/2018.
 */
public class AppHandlerTest {

    @Mock
    private LoginHandler mLoginHandler;

    @Mock
    private RegisterHandler mRegisterHandler;

    @Mock
    private ContactHandler mContactHandler;

    @Mock
    private TeamHandler mTeamHandler;

    @Mock
    private ProjectHandler mProjectHandler;

    @Mock
    private ProfileHandler mProfileHandler;

    @Mock
    private UserSelectHandler mUserSelectHandler;

    @Mock
    private ImageUploadHandler mImageUploadHandler;

    private AppHandler mAppHandler = AppHandler.getInstance();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void UpdateCache_Test(){
        mAppHandler.updateCache();
        verify(mContactHandler).updateCache();
//        verify(mLoginHandler).updateCache();
//        verify(mRegisterHandler).updateCache();
//        verify(mTeamHandler).updateCache();
//        verify(mProjectHandler).updateCache();
//        verify(mProfileHandler).updateCache();
//        verify(mUserSelectHandler).updateCache();
//        verify(mImageUploadHandler).updateCache();
    }


}