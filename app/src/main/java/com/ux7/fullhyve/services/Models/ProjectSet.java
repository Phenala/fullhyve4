package com.ux7.fullhyve.services.Models;

import com.ux7.fullhyve.services.Utility.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// projects
public class ProjectSet{
    private HashMap<Integer, MyProject> myProjects;

    public ProjectSet(){
        this.myProjects = new HashMap<>();
    }

    public List<MyProject> getMyProjects(int offset, int limit){
        return Util.sliceArray((List<MyProject>) myProjects.values(), offset, limit);
    }

    public MyProject getMyProject(int projectId){
        return myProjects.get(projectId);
    }
}
