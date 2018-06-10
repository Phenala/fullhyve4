package com.ux7.fullhyve.services.Models;

/**
 * Created by TOSHIBA on 4/21/2018.
 */
public class MyTeam extends Team{
    public Announcement[] announcements;
    public User leader;
    public User[] members;
    public int unseenAnnounements;
    public Project[] projects;

}
