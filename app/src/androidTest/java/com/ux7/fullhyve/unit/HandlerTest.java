package com.ux7.fullhyve.unit;

import android.util.Log;

import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ux7.fullhyve.services.Handlers.Handler;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.services.Utility.Realtime;
import com.ux7.fullhyve.services.Utility.ResponseFormat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by Admin on 6/18/2018.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Gson.class, GsonBuilder.class})
public class HandlerTest {
    @Mock
    private AppData.Cache mCache;

    @Mock(name = "gson")
    private Gson mGson;

    @Mock(name = "gsonBuilder")
    private GsonBuilder mGsonBuilder;

    @Mock
    private AppData mAppData;

    @InjectMocks
    private Handler mHandler ;

    @Before
    public void setUp(){
        PowerMockito.mockStatic(GsonBuilder.class);
        PowerMockito.mockStatic(Gson.class);
        when(mGsonBuilder.create()).thenReturn(mGson);
        MockitoAnnotations.initMocks(this);

//
//        PowerMockito.mockStatic(Log.class);

//        PowerMockito.mockStatic(Realtime.class);

    }

    @Test
    public void GeneralHandler_returnsResponseFormatCode(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", 400);
        ResponseFormat responseFormat = new ResponseFormat();
//        when(mGson.fromJson(jsonObject.toString(), ResponseFormat.class)).thenReturn(responseFormat);
        assertEquals(400, mHandler.generalHandler(jsonObject));
//        verify(mGson).fromJson(jsonObject.toString(), ResponseFormat.class);
    }

    @Test
    public void GeneralHandler_returnsNullForZeroLengthArgs(){
        when(mGson.fromJson(new JsonObject().toString(),ResponseFormat.class)).thenReturn(new ResponseFormat());
        assertEquals(0, mHandler.generalHandler());
    }

    @Test
    public void UpdateCache_Test(){
        verify(mAppData).getCache();
    }


}