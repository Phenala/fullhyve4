package com.ux7.fullhyve.services.Models;

import java.io.Serializable;


public class Team implements Serializable{
    public int id;
    public String name;
    public String image;
    public String description;
    public int memberCount;

    public Team(int id, String name, String image, String description, int memberCount) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.memberCount = memberCount;
    }
}
