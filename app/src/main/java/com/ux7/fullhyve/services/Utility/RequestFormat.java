package com.ux7.fullhyve.services.Utility;

import org.json.JSONObject;

import java.util.HashMap;

public class RequestFormat {

    public static JSONObject createRequestObj(String action, HashMap<String, Object> args){
        JSONObject req = new JSONObject();
        JSONObject reqData = new JSONObject();
        try{
            req.put("action", action);
            for(String key: args.keySet()){
                reqData.put(key,args.get(key));
            }
            req.put("reqData", reqData);
        } catch (Exception e){
            return null;
        }

        return req;
    }
}
