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
        return Util.sliceArray(Util.castTo(new MyProject(0,"","","","",0), myProjects.values()), offset, limit);
    }

    public MyProject getMyProject(int projectId){
        if(myProjects.containsKey(projectId)){
            return myProjects.get(projectId);
        }
        return null;
    }

    public void addProject(MyProject project){
        myProjects.put(project.id, project);
    }

    public void addProjects(List<MyProject> projects){
        for(MyProject project:projects){
            myProjects.put(project.id,project);
        }
    }

    public void editProjectProfile(Project project){
        if(myProjects.containsKey(project.id)){
            myProjects.get(project.id).updateProjectProfile(project);
        }
    }

    public void removeProject(int projectId){
        if(myProjects.containsKey(projectId)){
            myProjects.remove(projectId);
        }
    }
}
