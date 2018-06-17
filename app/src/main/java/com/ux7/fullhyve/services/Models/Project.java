package com.ux7.fullhyve.services.Models;

import java.io.Serializable;

public class Project{
    public int id;
    public String name;
    public String image;
    public String description;
    public String field;
    public int contributorCount;

    public Project(int id, String name, String image, String description, String field, int contributorCount) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.field = field;
        this.contributorCount = contributorCount;
    }
}
