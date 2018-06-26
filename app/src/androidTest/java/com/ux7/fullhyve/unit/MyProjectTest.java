package com.ux7.fullhyve.unit;

import android.util.Log;

import com.ux7.fullhyve.services.Models.MyProject;
import com.ux7.fullhyve.services.Models.Task;
import com.ux7.fullhyve.services.Models.TaskSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Admin on 6/22/2018.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class MyProjectTest {

    private MyProject mMyProject;

    @Before
    public void setUp(){
        mMyProject = new MyProject(1,"ProjectName","ProjectImage","ProjectDesc","ProjectField", 34);
        PowerMockito.mockStatic(Log.class);
    }

    @Test
    public void UpdateProjectProfile_Test(){
        MyProject newProject = new MyProject(1, "", "", "", "", 34);
        mMyProject.updateProjectProfile(newProject);
        assertEquals(newProject.name, mMyProject.name);
        assertEquals(newProject.image, mMyProject.image);
        assertEquals(newProject.description, mMyProject.description);
        assertEquals(newProject.field, mMyProject.field);
    }

    @Test
    public void AddTaskSet_Test(){
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        TaskSet taskSet = new TaskSet(1,"",3,"","",0,tasks);
        mMyProject.addTaskSet(taskSet);
        assertEquals(taskSet, mMyProject.getTaskset(taskSet.id));
    }

    @Test
    public void RemoveTaskSet_Test(){
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        TaskSet taskSet = new TaskSet(1,"",3,"","",0,tasks);
        TaskSet taskSet1 = new TaskSet(2,"",3,"","",0,tasks);

        mMyProject.addTaskSet(taskSet);
        mMyProject.addTaskSet(taskSet1);
        mMyProject.removeTaskSet(taskSet.id);
        assertEquals(1,mMyProject.taskSets.size());
        assertEquals(null, mMyProject.getTaskset(taskSet.id));
        assertEquals(taskSet1, mMyProject.getTaskset(taskSet1.id));
    }

    @Test
    public void LoadTaskSets_Test(){
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        TaskSet taskSet = new TaskSet(1,"",3,"","",0,tasks);
        TaskSet taskSet1 = new TaskSet(2,"",3,"","",0,tasks);

        ArrayList<TaskSet> taskSets = new ArrayList<>();
        taskSets.add(taskSet);
        taskSets.add(taskSet1);

        mMyProject.loadTaskSets(taskSets);
        assertEquals(taskSets, mMyProject.taskSets);

    }

    @Test
    public void GetTaskSets_Test(){
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        TaskSet taskSet = new TaskSet(1,"",3,"","",0,tasks);
        TaskSet taskSet1 = new TaskSet(2,"",3,"","",0,tasks);

        ArrayList<TaskSet> taskSets = new ArrayList<>();
        taskSets.add(taskSet);
        taskSets.add(taskSet1);

        mMyProject.loadTaskSets(taskSets);
        assertEquals(taskSets.get(0), mMyProject.getTaskset(taskSet.id));
        assertEquals(taskSets.get(1), mMyProject.getTaskset(taskSet1.id));
        assertEquals(taskSets, mMyProject.taskSets);
    }

    @Test
    public void GetTaskSet_Test(){
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        TaskSet taskSet = new TaskSet(1,"",3,"","",0,tasks);
        mMyProject.addTaskSet(taskSet);
        assertEquals(taskSet, mMyProject.taskSets.get(0));
    }
}