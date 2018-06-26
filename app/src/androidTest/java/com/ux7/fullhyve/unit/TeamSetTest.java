package com.ux7.fullhyve.unit;

import android.util.Log;

import com.ux7.fullhyve.services.Models.MyTeam;
import com.ux7.fullhyve.services.Models.SendAnnouncement;
import com.ux7.fullhyve.services.Models.TeamSet;

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

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;

/**
 * Created by Admin on 6/18/2018.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class TeamSetTest {
    private TeamSet mTeamSet;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mTeamSet = new TeamSet();

        PowerMockito.mockStatic(Log.class);
    }

    @Test
    public void GetTeam_Test(){
        ArrayList<MyTeam> myTeams = new ArrayList<>();
        MyTeam team1 = new MyTeam(1, "", "","egrg",455);
        MyTeam team2 = new MyTeam(2,"","", "egrt", 34);


        myTeams.add(team1);
        myTeams.add(team2);

        mTeamSet.addTeams(myTeams);
        assertEquals(team1, mTeamSet.getTeam(team1.id));
        assertEquals(team2, mTeamSet.getTeam(team2.id));
        assertEquals(null, mTeamSet.getTeam(3));
    }

    @Test
    public void GetMyTeam_Test(){
        ArrayList<MyTeam> myTeams = new ArrayList<>();
        MyTeam team1 = new MyTeam(1, "","","",34);
        MyTeam team2 = new MyTeam(2,"","","",23);
        myTeams.add(team1);
        myTeams.add(team2);

        mTeamSet.addTeams(myTeams);

        assertEquals(myTeams, mTeamSet.getMyTeams(0,2));
        assertEquals(myTeams,mTeamSet.getMyTeams(0,3));
        assertEquals(myTeams,mTeamSet.getMyTeams(0,4));
    }

    @Test
    public void EditTeamProfile_Test(){
        MyTeam myTeam = new MyTeam(1,"","","", 34);
        mTeamSet.addTeam(myTeam);
        myTeam.name = "er";
        myTeam.image = "er";
        myTeam.description = "er";
        mTeamSet.editTeamProfile(myTeam);
        assertEquals(myTeam.name,mTeamSet.getTeam(1).name);
        assertEquals(myTeam.image, mTeamSet.getTeam(1).image);
        assertEquals(myTeam.description, mTeamSet.getTeam(1).description);
    }

    @Test
    public void AddTeams_Test(){
        ArrayList<MyTeam> myTeams = new ArrayList<>();
        myTeams.add(new MyTeam(1,"","","",34));
        myTeams.add(new MyTeam(2, "","","",45));
        mTeamSet.addTeams(myTeams);
        assertEquals(myTeams,mTeamSet.getMyTeams(0,2));
    }

    @Test
    public void AddTeam_Test(){
        MyTeam myTeam = new MyTeam(1,"","","",44);
        mTeamSet.addTeam(myTeam);
        assertEquals(myTeam,mTeamSet.getTeam(myTeam.id));
    }

    @Test
    public void RemoveTeam_Test(){
        MyTeam myTeam = new MyTeam(1,"","","",44);
        mTeamSet.addTeam(myTeam);
        mTeamSet.removeTeam(myTeam.id);
        assertEquals(null,mTeamSet.getTeam(1));
    }

    @Test
    public void RemoveSendAnnouncement_Test(){
        SendAnnouncement sendAnnouncement = new SendAnnouncement(1,"");
        int tempId = mTeamSet.addSendAnnouncement(sendAnnouncement);
        mTeamSet.removeSendAnnouncement(tempId);
        assertEquals(null,mTeamSet.sendAnnouncementQueue.get(tempId));
    }

    @Test
    public  void AddSendAnnouncement_Test(){
        SendAnnouncement sendAnnouncement = new SendAnnouncement(1,"");
        int tempId = mTeamSet.addSendAnnouncement(sendAnnouncement);
        assertEquals(sendAnnouncement, mTeamSet.sendAnnouncementQueue.get(tempId));
    }


}