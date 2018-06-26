package com.ux7.fullhyve.unit;

import android.util.Log;

import com.ux7.fullhyve.services.Models.MyProject;
import com.ux7.fullhyve.services.Models.ProjectSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Admin on 6/18/2018.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class ProjectSetTest {
    private ProjectSet mProjectSet;

    @Before
    public void setup(){

        mProjectSet = new ProjectSet();

        PowerMockito.mockStatic(Log.class);
    }

    @Test
    public void GetMyProjects_Test(){
        ArrayList<MyProject> myProjects = new ArrayList<>();
        myProjects.add(new MyProject(1,"","","","",2));
        myProjects.add(new MyProject(2,"","","","",5));
        mProjectSet.addProjects(myProjects);
//        assertEquals(null,mProjectSet.getMyProjects(0,1));
        assertEquals(myProjects,mProjectSet.getMyProjects(0,2));
        assertEquals(myProjects,mProjectSet.getMyProjects(0,3));
        assertEquals(myProjects,mProjectSet.getMyProjects(0,4));
    }

    @Test
    public void GetMyProject_Test(){
        MyProject myProject = new MyProject(1,"","","","",4);
        mProjectSet.addProject(myProject);
        assertEquals(myProject,mProjectSet.getMyProject(myProject.id));
        assertEquals(null,mProjectSet.getMyProject(3));
    }

    @Test
    public void AddProject_Test(){
        MyProject myProject = new MyProject(1,"","","","",34);
        mProjectSet.addProject(myProject);
        assertEquals(myProject, mProjectSet.getMyProject(1));
    }

    @Test
    public void AddProjects_Test(){
        ArrayList<MyProject> myProjects = new ArrayList<>();
        myProjects.add(new MyProject(1,"","","","",2));
        myProjects.add(new MyProject(2,"","","","",5));
        mProjectSet.addProjects(myProjects);

        assertEquals(myProjects,mProjectSet.getMyProjects(0,2));
        assertEquals(myProjects,mProjectSet.getMyProjects(0,3));
        assertEquals(myProjects,mProjectSet.getMyProjects(0,4));
    }

    @Test
    public void EditProjectProfile_Test(){
        MyProject myProject = new MyProject(1,"","","","",4);
        mProjectSet.addProject(myProject);
        myProject.name = "er";
        myProject.image = "er";
        myProject.description = "er";
        myProject.field = "er";


        mProjectSet.editProjectProfile(myProject);

        assertEquals(myProject,mProjectSet.getMyProject(myProject.id));
    }

    @Test
    public void RemoveProject_Test(){
        MyProject myProject = new MyProject(1,"","","","",3);
        mProjectSet.addProject(myProject);
        mProjectSet.removeProject(myProject.id);
        assertEquals(null,mProjectSet.getMyProject(myProject.id));
    }
}