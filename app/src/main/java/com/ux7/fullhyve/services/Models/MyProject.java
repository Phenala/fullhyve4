package com.ux7.fullhyve.services.Models;


import com.ux7.fullhyve.services.Utility.Util;

import java.util.ArrayList;
import java.util.List;

public class MyProject extends Project{
    public User leader;
    public ContributorSet contributors;
    public List<TaskSet> taskSets;

    public MyProject(int id, String name, String image, String description, String field, int contributorCount) {
        super(id, name, image, description, field, contributorCount);

        taskSets = new ArrayList<>();
        contributors = new ContributorSet();
    }

    public void updateProjectProfile(Project project){
        this.name = project.name;
        this.image = project.image;
        this.description = project.description;
        this.field = project.field;
    }

    public void addTaskSet(TaskSet taskSet){
        taskSets.add(taskSet);
    }

    public void removeTaskSet(int taskSetId){
        for(int i=0;i<taskSets.size();i++){
            if(taskSets.get(i).id == taskSetId){
                taskSets.remove(i);
                return;
            }
        }
    }

    public void loadTaskSets(List<TaskSet> taskSets){
        this.taskSets.addAll(taskSets);
    }

    public List<TaskSet> getTaskSets(int offset, int limit){
        return Util.sliceArray(taskSets,offset, limit);
    }

    public TaskSet getTaskset(int taskSetId){
        for(TaskSet taskSet:taskSets){
            if(taskSet.id == taskSetId){
                return taskSet;
            }
        }
        return null;
    }
}
