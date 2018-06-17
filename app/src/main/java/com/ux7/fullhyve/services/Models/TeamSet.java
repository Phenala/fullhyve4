package com.ux7.fullhyve.services.Models;


import com.ux7.fullhyve.services.Utility.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamSet{
    public HashMap<Integer, MyTeam> myTeams;
    public HashMap<Integer, SendAnnouncement> sendAnnouncementQueue;

    public TeamSet() {
        myTeams = new HashMap<>();
        sendAnnouncementQueue = new HashMap<>();
    }

    public MyTeam getTeam(int teamId){
        if(myTeams.containsKey(teamId)){
            return myTeams.get(teamId);
        }
        return null;
    }

    public List<MyTeam> getMyTeams(int offset, int limit){
        return Util.sliceArray(new ArrayList<>(myTeams.values()), offset, limit);
    }

    public void editTeamProfile(MyTeam team){
        if(myTeams.containsKey(team.id)){
            myTeams.put(team.id,team);
        }
    }

    public void addTeams(ArrayList<MyTeam> teams){
        for(MyTeam team:teams){
            myTeams.put(team.id,team);
        }
    }

    public void addTeam(MyTeam team){
        myTeams.put(team.id,team);
    }

    public void removeTeam(int teamId){
        if(myTeams.containsKey(teamId)){
            myTeams.remove(teamId);
        }
    }

    public int addSendAnnouncement(SendAnnouncement sendAnnouncement){
        sendAnnouncementQueue.put(sendAnnouncement.getTempId(),sendAnnouncement);
        return sendAnnouncement.getTempId();
    }

    public void removeSendAnnouncement(int tempId){
        sendAnnouncementQueue.remove(tempId);
    }
}
