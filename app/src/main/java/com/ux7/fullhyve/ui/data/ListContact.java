package com.ux7.fullhyve.ui.data;

import java.io.Serializable;

/**
 * Created by hp on 4/17/2018.
 */


public class ListContact implements Serializable {

    public int id = 23456;
    public String image = "http://0.gravatar.com/avatar/c77b7988df1396d40ed4a62be4e55565?s=64&d=mm&r=g";
    public String name = "Rod Rodder";
    public String lastMessage = "Gangsta to the core.";
    public boolean lastMessageSent = false;
    public boolean searchResult = false;
    public int newMessages = 2;
    public UserDetail userDetail;

}
