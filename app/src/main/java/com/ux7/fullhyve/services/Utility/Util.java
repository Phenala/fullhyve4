package com.ux7.fullhyve.services.Utility;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class Util {
    public static <T> List<T> sliceArray(List<T> arrayData, int offset, int limit){
        Log.e("Array size",arrayData.size()+"");
        if(offset < 0 || limit <= 0 || offset >= arrayData.size()){
            Log.e("Array","Is null");
            return null;
        }
        return arrayData.subList(offset, offset + limit>=arrayData.size()?arrayData.size():offset+limit);
    }

    public static <T, Y> List<T> castTo (T type, Collection<Y> values) {
        List<T> newList = new ArrayList<>();
        for (Object value : values)
            newList.add((T)value);
        return newList;
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
