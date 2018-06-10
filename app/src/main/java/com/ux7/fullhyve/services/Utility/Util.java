package com.ux7.fullhyve.services.Utility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Util {
    public static <T> ArrayList<T> sliceArray(ArrayList<T> arrayData, int offset, int limit){
        if(offset < 0 || limit <= 0 && offset >= arrayData.size()){
            return null;
        }
        return (ArrayList<T>) arrayData.subList(offset, offset + limit);
    }


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
