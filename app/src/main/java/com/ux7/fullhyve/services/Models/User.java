package com.ux7.fullhyve.services.Models;


import java.io.Serializable;


public class User implements Serializable{
    private int id;
    private String firstName;
    private String lastName;
    private String image;
    private String title;
    public int request;
    private String[] skills;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {

        return (firstName.charAt(0) + "").toUpperCase() + firstName.substring(1);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {

        return (lastName.charAt(0) + "").toUpperCase() + lastName.substring(1);
    }

    public String getName() {
        return getFirstName() + " " + getLastName();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


