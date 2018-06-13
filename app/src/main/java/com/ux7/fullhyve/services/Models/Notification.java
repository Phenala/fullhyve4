package com.ux7.fullhyve.services.Models;


import java.io.Serializable;

public class Notification implements Serializable{
    public int id;
    public String image;
    public String comment;
    //public Link[] commentLinks;
    //public Link[] optionLinks;
    public Link[] options;

    @Override
    public String toString() {
        return "Comment: " + comment + " - ID: " + id;
    }
}
