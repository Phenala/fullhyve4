package com.ux7.fullhyve.services.Models;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class TaskSet{
    public int id;
    public String name;
    public int number;
    public String deadline;
    public String description;
    public int assignment;
    public int completion;
    public List<Task> tasks;

    public TaskSet(int id, String name, int number, String deadline, String description, int completion, ArrayList<Task> tasks) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.deadline = deadline;
        this.description = description;
        this.completion = completion;
        this.tasks = tasks;
    }
}
