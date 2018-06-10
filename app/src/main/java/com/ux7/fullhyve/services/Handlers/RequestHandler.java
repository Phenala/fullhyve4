package com.ux7.fullhyve.services.Handlers;


import com.ux7.fullhyve.services.Utility.ResponseListener;

public class RequestHandler {
    public void post(Request request, ResponseListener responseListener){
        // send request, fetch response
        Object response = null;
        responseListener.call(response);
    }
    public void get(Request request, ResponseListener responseListener){
        // send request, fetch response
        Object response = null;
        responseListener.call(response);
    }
}


class Request{
    public String url;
    public RequestData data;
}

class RequestData{
    public Object other;    // values like teamId, projectId are entered here
    public Object store;    // objects to be stored in database are entered here as a single object or array
}