package com.ux7.fullhyve.services.Handlers;


import android.util.Log;

public class AppHandler{
    public LoginHandler loginHandler;
    public RegisterHandler registerHandler;
    public ContactHandler contactHandler;
    public TeamHandler teamHandler;
    public ProjectHandler projectHandler;
    public ProfileHandler profileHandler;
    public UserSelectHandler userSelectHandler;
    public ImageUploadHandler imageUploadHandler;

    private static AppHandler instance = new AppHandler();

    public static AppHandler getInstance(){
        if(instance == null){
            instance = new AppHandler();
        }
        return instance;
    }

    private AppHandler(){
        loginHandler = new LoginHandler();
        registerHandler = new RegisterHandler();
        contactHandler = new ContactHandler();
        teamHandler = new TeamHandler();
        projectHandler = new ProjectHandler();
        profileHandler = new ProfileHandler();
        userSelectHandler = new UserSelectHandler();
        imageUploadHandler = new ImageUploadHandler();
    }

    public void updateCache() {

        Handler.updateCache();
        LoginHandler.updateCache();
        RegisterHandler.updateCache();
        ContactHandler.updateCache();
        TeamHandler.updateCache();
        ProjectHandler.updateCache();
        ProfileHandler.updateCache();
        UserSelectHandler.updateCache();
        ImageUploadHandler.updateCache();

    }

}
