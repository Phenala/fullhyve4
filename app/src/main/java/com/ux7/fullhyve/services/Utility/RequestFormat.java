package com.ux7.fullhyve.services.Utility;

import android.util.Log;

import com.google.gson.JsonElement;
import com.ux7.fullhyve.services.Storage.AppData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.HashMap;



public class RequestFormat {
    public static AppData.Cache cache = AppData.getCache();

    public static JSONObject createRequestObj(String action, HashMap<String, Object> args){
        JSONObject req = new JSONObject();
        JSONObject reqData = new JSONObject();
        try{
            req.put("action", action);

            req.put("token", "Bearer " + (cache.getToken()==null?"":cache.getToken()));
            Log.e("Token in request", cache.getToken()==null?"No token":cache.getToken());
            if(args != null){
                for(String key: args.keySet()){
                    reqData.put(key,args.get(key));
                }
            }

            req.put("reqData", reqData);
        } catch (Exception e){
            Log.e("JSONObject",e.getMessage());
            return null;
        }

        return req;
    }


    public static JsonElement createRequestObj(Object arg, String action){
        Gson gson = new GsonBuilder().create();

        HashMap<String, Object> req = new HashMap<>();

        req.put("action", action);
        req.put("token", "Bearer " + (cache.getToken()==null?"":cache.getToken()));
        req.put("reqData", arg==null?new HashMap<String,Object>():arg);

        return gson.toJsonTree(req);
    }
}
