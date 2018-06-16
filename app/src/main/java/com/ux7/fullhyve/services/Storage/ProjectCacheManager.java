package com.ux7.fullhyve.services.Storage;


import com.ux7.fullhyve.services.Models.ContributorSet;
import com.ux7.fullhyve.services.Models.MyProject;
import com.ux7.fullhyve.services.Models.Project;
import com.ux7.fullhyve.services.Models.Task;
import com.ux7.fullhyve.services.Models.TaskSet;
import com.ux7.fullhyve.services.Utility.Util;

import java.util.List;

public class ProjectCacheManager {

    public static List<MyProject> getMyProjects(int offset, int limit){
        return AppData.getCache().getProjects().getMyProjects(offset, limit);
    }

    public static void editProjectDetails(Project project){
        AppData.getCache().getProjects().editProjectProfile(project);
    }

    public static void searchProjects(String name, int offset, int limit){

    }

    public static List<ContributorSet> getContributors(int projectId, int offset, int limit){
        AppData.Cache cache = AppData.getCache();
        MyProject myProject = cache.getProjects().getMyProject(projectId);

        if(myProject != null){

        }
        //return AppData.getCache().getProjects().getMyProject(projectId);
        return null;
    }

    public static List<TaskSet> getTaskSets(int offset, int limit){
        return null;
    }

    public static List<Task> getTasks(int offset, int limit){
        return null;
    }

    public static void newProject(){

    }


}
