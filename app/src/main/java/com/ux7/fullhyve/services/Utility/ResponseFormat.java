package com.ux7.fullhyve.services.Utility;


import com.ux7.fullhyve.services.Models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResponseFormat{
    public boolean success;

    public int code;

    public String message;

    @Override
    public String toString() {
        return "Message: "+message;
    }


    // server triggered
    public class DisconnectedUser{
        public int userId;
        public String timestamp;
    }
    public class DisconnectedUserR{
        public DisconnectedUser data;
    }









    public class Counted{
        public boolean done;
    }

    public class SendMessage{
        public int msgId;
    }

    public class MessageR{
        public SendMessage data;
    }

    // received message listener
    public class ReceivedMessages{
        public int senderId;
        public List<Message> messages;
    }

    public class ReceivedMessagesR{
        public List<ReceivedMessages> data;
    }

    // getMessages listener
    public class GetMessages extends Counted{
        public List<Message> messages;
    }

    public class GetMessagesR{
        public GetMessages data;
    }

    // getFriends

    public class GetFriends extends Counted{
        public List<Contact> friends;
    }

    public class GetFriendsR{
        public GetFriends data;
    }

    //get Users
    public class SearchUsers extends GetFriends{
        public List<User> users;
    }
    public class SearchUsersR{
        public SearchUsers data;
    }

    //getNotifications
    public class GetNotifications extends Counted{
        public ArrayList<Notification> notifications;
    }
    public class GetNotificationsR{
        public GetNotifications data;
    }

    //getLastSeen
    public class GetLastOnline{
        public boolean online;
        public String timestamp;
    }

    public class GetLastOnlineR{
        public GetLastOnline data;
    }

    /// teams
    //=========================================================

    public class GetTeamsResponse{
        public boolean done;
        public List<MyTeam> myTeams;
    }
    public class GetTeamsR{
        public GetTeamsResponse data;
    }
    public class TeamMember{
        public boolean done;
        public List<User> members;
    }
    public class GetTeamMemberR{
        public TeamMember data;
    }
    public class TeamProject{
        public boolean done;
        public List<Project> projects;
    }
    public class GetTeamProjectR{
        public TeamProject data;
    }
    public class TeamAnnouncement{
        public boolean done;
        public List<Announcement> announcements;
    }
    public class GetTeamAnnouncementR{
        public TeamAnnouncement data;
    }
    public class CreateNewTeamR{
        public int data;
    }
    public class AnnounceR{
        public int data;
    }
    public class StatusR{
        public Boolean data;
    }
    public class ReplyR{
        public int data;
    }
    public class GetTeamProfileR{
        public Team data;
    }


    // projects
    //===================================================

    public class MyProjects{
        public boolean done;
        public List<MyProject> myProjects;
    }
    public class CreateNewProject{
        public int data;
    }
    public class CreateNewTaskR{
        public int data;
    }
    public class CreateNewTasksetR{
        public int data;
    }
    public class GetMyProjectsR{
        public MyProjects data;
    }

    public class ProjectSearchResults{
        public boolean done;
        public List<Project> projects;
        public List<MyProject> myProjects;
    }
    public class SearchProjectsR{
        public ProjectSearchResults data;
    }
    public class ProjectContributors{
        public boolean done;
        public List<Team> teams;
        public List<User> individuals;
    }

    public class GetProjectContributorsR{
        public ProjectContributors data;
    }
    public class TaskSets{
        public boolean done;
        public List<TaskSet> taskSets;
    }
    public class GetTaskSetsR{
        public TaskSets data;
    }

    public class Tasks{
        public boolean done;
        public List<Task> tasks;
    }
    public class GetTasksR{
        public Tasks data;
    }

    // account
    //==================================================

    public class SignedIn{
        public String token;
    }
    public class SignInR{
        public SignedIn data;
    }

    public class GetProfileR{
        public Identity data;
    }
    public class GetProjectDetailR{
        public Project project;
    }
}

