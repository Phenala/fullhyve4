package com.ux7.fullhyve.services.Models;


import java.util.ArrayList;
import java.util.HashMap;

public class TeamSet{
    public HashMap<Integer, MyTeam> myTeams;
    public HashMap<Integer, Team> teams;
    public SendAnnouncement[] sendAnnouncementQueue;


    public HashMap<Integer, MyTeam> getMyTeams(){
        return myTeams;
    }

    public void setMyTeams(HashMap<Integer, MyTeam> myTeams) {
        this.myTeams = myTeams;
    }

    public HashMap<Integer, Team> getTeams() {
        return teams;
    }

    public void setTeams(HashMap<Integer, Team> teams) {
        this.teams = teams;
    }

    public SendAnnouncement[] getSendAnnouncementQueue() {
        return sendAnnouncementQueue;
    }

    public void setSendAnnouncementQueue(SendAnnouncement[] sendAnnouncementQueue) {
        this.sendAnnouncementQueue = sendAnnouncementQueue;
    }

    public ArrayList<MyTeam> getMyTeams(int offset, int limit){
        ArrayList<MyTeam> myTeamsL = (ArrayList<MyTeam>)getMyTeams().values();

        if(offset > myTeamsL.size() || offset < 0 || limit <= 0){
            return null;
        }

        return (ArrayList<MyTeam>) myTeamsL.subList(offset, (myTeamsL.size() > offset+limit)?offset+limit:myTeamsL.size());
    }
}
