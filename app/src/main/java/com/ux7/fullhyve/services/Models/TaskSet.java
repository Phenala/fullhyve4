package com.ux7.fullhyve.services.Models;

import java.io.Serializable;

/**
 * Created by TOSHIBA on 4/21/2018.
 */
public class TaskSet implements Serializable{
    public int id;
    public String name;
    public int number;
    public String deadline;
    public String description;
    public String completion;
    public Task[] tasks;
}
