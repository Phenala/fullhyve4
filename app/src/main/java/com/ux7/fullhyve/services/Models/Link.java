package com.ux7.fullhyve.services.Models;


import java.io.Serializable;

public class Link implements Serializable{
    public int type;
    public boolean navigation;
    public String name;
    public int id;
//    public String link;
//    public String feedback;


    public enum LinkType {

        FRIEND_REQUEST,
        TEAM_REQUEST,
        PROJECT_TEAM_REQUEST,
        PROJECT_INDIVIDUAL_REQUEST,
        ASSIGNMENT

    }
}

