package com.ux7.fullhyve.services.Models;

import java.io.Serializable;

/**
 * Created by TOSHIBA on 4/21/2018.
 */
public class Task implements Serializable{
    public int id;
    public int number;
    public String title;
    public String description;
    public int status;
    public String deadline;
    public User assigner;
    public User assignee;
    public Team proxyTeam;
}
