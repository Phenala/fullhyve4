package com.ux7.fullhyve.unit;

import com.ux7.fullhyve.services.Models.Announcement;
import com.ux7.fullhyve.services.Models.MyTeam;
import com.ux7.fullhyve.services.Models.Project;
import com.ux7.fullhyve.services.Models.Team;
import com.ux7.fullhyve.services.Models.TeamMessage;
import com.ux7.fullhyve.services.Models.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MyTeamTest {

    private MyTeam mMyTeam;


    @Before
    public void setupIdentity(){
        // MockitoAnnotations.initMocks(this);

        mMyTeam = new MyTeam(22,"myTeam","img","my team",12);
        }

        @Test
        public void updateTeamProfile_Test() {

            Team team = new Team(32,"team","img","team",22);
            mMyTeam.updateTeamProfile(team);
            assertEquals(team.description,mMyTeam.description);

        }

    @Test
    public void getAnnouncements_Test() {

        List<Announcement> announcements = new ArrayList<>();
        announcements.add(new Announcement());
        announcements.add(new Announcement());
        announcements.add(new Announcement());
        mMyTeam.addNewAnnouncements(announcements);
        assertEquals(announcements,mMyTeam.getAnnouncements(0,3));

    }

    @Test
    public void addAnnouncement_Test() {

        List<Announcement> announcements = new ArrayList<>();
        Announcement announcement = new Announcement();
        announcements.add(announcement);
        mMyTeam.addAnnouncement(announcement);
        assertEquals(announcements,mMyTeam.getAnnouncements(0,1));

    }

    @Test
    public void removeAnnouncement_Test() {

        List<Announcement> announcements = new ArrayList<>();
        Announcement announcement = new Announcement();
        announcements.add(announcement);
        mMyTeam.addNewAnnouncements(announcements);
        TeamMessage teamMessage = Mockito.mock(TeamMessage.class);
        announcement.mainMessage = teamMessage;
        teamMessage.setId(22);
        mMyTeam.removeAnnouncement(announcement.mainMessage.getId());
        assertEquals(null,mMyTeam.getAnnouncement(22));

    }

    @Test
    public void editAnnouncement_Test() {

        List<Announcement> announcements = new ArrayList<>();
        Announcement announcement = new Announcement();
        announcements.add(announcement);
        mMyTeam.addNewAnnouncements(announcements);
        TeamMessage teamMessage = new TeamMessage(22,"","",true,true);
        announcement.mainMessage = teamMessage;
        mMyTeam.editAnnouncement(22,"edited");
        assertEquals("edited",teamMessage.getMessage());

    }

    @Test
    public void addProjects_Test() {

        List<Project> projects = new ArrayList<>();
        Project project =Mockito.mock(Project.class);
        projects.add(project);
        mMyTeam.addProjects(projects);
        assertEquals(projects,mMyTeam.getProjects(0,1));
    }

    @Test
    public void addMember_Test() {

        List<User> users = new ArrayList<>();
        User user = new User();
        users.add(user);
        mMyTeam.addMembers(users);
        assertEquals(mMyTeam.getMembers(0,1),users);
    }

    @Test
    public void removeMember_Test() {

        List<User> users = new ArrayList<>();
        User user = new User();
        users.add(user);
        mMyTeam.addMembers(users);
        int ids[] = {user.getId()};
        mMyTeam.removeMember(ids);
        assertEquals(null,mMyTeam.getMembers(0,1));
    }

}