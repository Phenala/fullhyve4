package com.ux7.fullhyve.services.Utility;

import com.ux7.fullhyve.services.Handlers.UserSelectHandler;
import com.ux7.fullhyve.services.Models.Announcement;
import com.ux7.fullhyve.services.Models.Contact;
import com.ux7.fullhyve.services.Models.Message;
import com.ux7.fullhyve.services.Models.MyProject;
import com.ux7.fullhyve.services.Models.MyTeam;
import com.ux7.fullhyve.services.Models.Project;
import com.ux7.fullhyve.services.Models.Task;
import com.ux7.fullhyve.services.Models.TaskSet;
import com.ux7.fullhyve.services.Models.Team;
import com.ux7.fullhyve.services.Models.TeamMessage;
import com.ux7.fullhyve.services.Models.User;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.ui.data.ListAnnouncement;
import com.ux7.fullhyve.ui.data.ListContact;
import com.ux7.fullhyve.ui.data.ListMember;
import com.ux7.fullhyve.ui.data.ListMessage;
import com.ux7.fullhyve.ui.data.ListProject;
import com.ux7.fullhyve.ui.data.ListReply;
import com.ux7.fullhyve.ui.data.ListTask;
import com.ux7.fullhyve.ui.data.ListTaskSet;
import com.ux7.fullhyve.ui.data.ListTeam;
import com.ux7.fullhyve.ui.data.ProjectDetail;
import com.ux7.fullhyve.ui.data.TaskDetail;
import com.ux7.fullhyve.ui.data.TaskSetDetail;
import com.ux7.fullhyve.ui.data.TeamDetail;
import com.ux7.fullhyve.ui.data.UserDetail;
import com.ux7.fullhyve.ui.enums.TaskStatus;
import com.ux7.fullhyve.ui.util.U;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static List<ListMessage> portMessageToListMessage(List<Message> messages) {

        List<ListMessage> uiMessages = new ArrayList<>();

        for (Message message : messages) {

            ListMessage lstmsg = portMessageToListMessage(message);
            uiMessages.add(lstmsg);

        }

        return uiMessages;

    }

    public static ListMessage portMessageToListMessage(Message message) {

        ListMessage lstmsg = new ListMessage();
        lstmsg.id = message.getId();
        lstmsg.message = message.getMessage();
        lstmsg.sent = message.isSent();
        lstmsg.time = U.getTime(message.getTimestamp());

        return lstmsg;

    }






    public static List<ListContact> portContactToListContact(List<Contact> contacts) {

        List<ListContact> uiContacts = new ArrayList<>();

        for (Contact contact: contacts) {

            ListContact nContact = portContactToListContact(contact);
            uiContacts.add(nContact);

        }

        return uiContacts;

    }

    public static ListContact portContactToListContact(Contact contact) {

        ListContact nContact = new ListContact();

        nContact.id = contact.getId();
        nContact.image = contact.getImage();
        nContact.name = contact.getFirstName() + " " + contact.getLastName();
        nContact.newMessages = contact.getUnseenMessages();
        if (contact.lastMessage != null) {
            nContact.lastMessage = contact.lastMessage.getMessage();
            nContact.lastMessageSent = contact.lastMessage.isSent();
        } else {
            nContact.lastMessageSent = false;
            nContact.lastMessage = "";
        }

        return nContact;

    }







    public static List<ListProject> portProjectToListProject(List<Project> projects) {


        List<ListProject> uiProjects = new ArrayList<>();

        for (Project project : projects) {

            ListProject nProject = portProjectToListProject(project);

            uiProjects.add(nProject);

        }

        return uiProjects;

    }

    public static List<ListProject> portMyProjectToListProject(List<MyProject> projects) {


        List<ListProject> uiProjects = new ArrayList<>();

        for (MyProject project : projects) {

            ListProject nProject = portProjectToListProject(project);

            uiProjects.add(nProject);

        }

        return uiProjects;

    }

    public static ListProject portProjectToListProject(Project project) {

        ListProject nProject = new ListProject();

        nProject.id = project.id;
        nProject.name = project.name;
        nProject.image = project.image;
        nProject.contributor = false;

        ProjectDetail projectDetail = new ProjectDetail();

        projectDetail.id = project.id;
        projectDetail.name = project.name;
        projectDetail.contributors = project.contributorCount;
        projectDetail.description = project.description;
        projectDetail.focus = project.field;
        projectDetail.image = project.image;

        nProject.detail = projectDetail;

        return nProject;

    }

    public static ListProject portProjectToListProject(MyProject project) {

        ListProject nProject = new ListProject();

        nProject.id = project.id;
        nProject.name = project.name;
        nProject.image = project.image;
        nProject.contributor = true;

        ProjectDetail projectDetail = new ProjectDetail();

        projectDetail.id = project.id;
        projectDetail.name = project.name;
        projectDetail.contributors = project.contributorCount;
        projectDetail.description = project.description;
        projectDetail.focus = project.field;
        projectDetail.image = project.image;
        projectDetail.leaderId = project.leader.getId();

        nProject.detail = projectDetail;

        return nProject;

    }







    public static List<ListTeam> portTeamToListTeam(List<Team> teams) {

        List<ListTeam> uiTeams = new ArrayList<>();

        for (Team team : teams) {

            ListTeam nTeam = portTeamToListTeam(team);

            uiTeams.add(nTeam);

        }

        return uiTeams;

    }

    public static List<ListTeam> portMyTeamToListTeam(List<MyTeam> teams) {

        List<ListTeam> uiTeams = new ArrayList<>();

        for (MyTeam team : teams) {

            ListTeam nTeam = portTeamToListTeam(team);

            uiTeams.add(nTeam);

        }

        return uiTeams;

    }

    public static ListTeam portTeamToListTeam(Team team) {

        ListTeam nTeam = new ListTeam();

        nTeam.id = team.id;
        nTeam.name = team.name;
        nTeam.image = team.image;
        nTeam.member = false;

        TeamDetail teamDetail = new TeamDetail();

        teamDetail.id = team.id;
        teamDetail.name = team.name;
        teamDetail.image = team.image;
        teamDetail.focus = team.focus;
        teamDetail.description = team.description;
        teamDetail.members = team.memberCount;

        nTeam.detail = teamDetail;

        return nTeam;

    }

    public static ListTeam portTeamToListTeam(MyTeam team) {

        ListTeam nTeam = new ListTeam();

        nTeam.id = team.id;
        nTeam.name = team.name;
        nTeam.image = team.image;
        nTeam.member = true;

        TeamDetail teamDetail = new TeamDetail();

        teamDetail.id = team.id;
        teamDetail.name = team.name;
        teamDetail.image = team.image;
        teamDetail.focus = team.focus;
        teamDetail.description = team.description;
        teamDetail.members = team.memberCount;
        teamDetail.leaderId = team.leader.getId();

        nTeam.detail = teamDetail;

        return nTeam;

    }






    public static List<ListAnnouncement> portAnnouncementToListAnnouncement (List<Announcement> announcements) {

        List<ListAnnouncement> uiAnnouncement = new ArrayList<>();

        for (Announcement announcement : announcements) {

            ListAnnouncement nAnnouncement = portAnnouncementToListAnnouncement(announcement);

            uiAnnouncement.add(nAnnouncement);

        }

        return uiAnnouncement;

    }

    public static ListAnnouncement portAnnouncementToListAnnouncement (Announcement announcement) {

        ListAnnouncement nAnnouncement = new ListAnnouncement();

        nAnnouncement.id = announcement.mainMessage.getId();
        nAnnouncement.message = announcement.mainMessage.getMessage();
        nAnnouncement.replies = announcement.replies.size();
        nAnnouncement.senderId = announcement.mainMessage.sender.getId();
        nAnnouncement.senderImage = announcement.mainMessage.sender.getImage();
        nAnnouncement.senderName = announcement.mainMessage.sender.getFirstName() + " " + announcement.mainMessage.sender.getLastName();
        nAnnouncement.sent = announcement.mainMessage.isSent();
        nAnnouncement.sentTime = U.getTime(announcement.mainMessage.getTimestamp());
        nAnnouncement.listReplies = portReplyToListReply(announcement.replies);

        return nAnnouncement;

    }

    public static  List<ListReply> portReplyToListReply (List<TeamMessage> messages) {

        List<ListReply> replies = new ArrayList<>();

        for (TeamMessage message : messages) {

            replies.add(portReplyToListReply(message));

        }

        return replies;

    }


    public static ListReply portReplyToListReply (TeamMessage message) {

        ListReply reply = new ListReply();

        reply.id = message.getId();
        reply.reply = message.getMessage();
        reply.senderImage = message.sender.getImage();
        reply.senderName = message.sender.getFirstName() + " " + message.sender.getLastName();
        reply.time = U.getTime(message.getTimestamp());
        reply.senderId = message.sender.getId();

        return reply;

    }






    public static List<ListTaskSet> portTaskSetToListTaskSet (List<TaskSet> taskSets) {

        List<ListTaskSet> uiTaskSet = new ArrayList<>();

        for (TaskSet taskSet : taskSets) {

            ListTaskSet listTaskSet = portTaskSetToListTaskSet(taskSet);

            uiTaskSet.add(listTaskSet);

        }

        return uiTaskSet;

    }

    public static  ListTaskSet portTaskSetToListTaskSet (TaskSet taskSet) {

        ListTaskSet nTaskSet = new ListTaskSet();
        int id = AppData.getInstance().getCache().getIdentity().getId();

        nTaskSet.id = taskSet.id;
        nTaskSet.name = taskSet.name;
        nTaskSet.number = taskSet.number;
        nTaskSet.completion = taskSet.completion;
        nTaskSet.assigments = taskSet.assignment;

        TaskSetDetail detail = new TaskSetDetail();

        detail.id = nTaskSet.id;
        detail.name = nTaskSet.name;
        detail.assigments = nTaskSet.assigments;
        detail.completion = nTaskSet.completion;
        detail.deadline = taskSet.deadline;
        detail.description = taskSet.description;
        detail.number = nTaskSet.number;

        nTaskSet.detail = detail;

        return nTaskSet;

    }







    public static  List<ListTask> portTaskToListTask (List<Task> tasks) {

        List<ListTask> listTasks = new ArrayList<>();

        for (Task task : tasks) {

            ListTask listTask = portTaskToListTask(task);

            listTasks.add(listTask);
        }

        return listTasks;

    }

    public static ListTask portTaskToListTask (Task task) {

        ListTask listTask = new ListTask();

        listTask.id = task.id;
        listTask.name = task.title;
        listTask.assigneeId = task.assignee.getId();
        listTask.assigneeImage = task.assignee.getImage();
        listTask.number = task.number;

        TaskStatus[] statuses = new TaskStatus[] {TaskStatus.WAITING, TaskStatus.INPROGRESS, TaskStatus.PENDINGEVALUATION, TaskStatus.APPROVED, TaskStatus.PENDINGREVISION};

        listTask.detail = portTaskToTaskDetail(task);
        listTask.status = statuses[task.status];

        return listTask;

    }


    public static TaskDetail portTaskToTaskDetail (Task task) {

        TaskDetail taskDetail = new TaskDetail();

        taskDetail.id = task.id;
        taskDetail.name = task.title;
        taskDetail.assigneeId = task.assignee.getId();
        taskDetail.assigneeImage = task.assignee.getImage();
        taskDetail.number = task.number;

        TaskStatus[] statuses = new TaskStatus[] {TaskStatus.WAITING, TaskStatus.INPROGRESS, TaskStatus.PENDINGEVALUATION, TaskStatus.APPROVED, TaskStatus.PENDINGREVISION};

        taskDetail.status = statuses[task.status];

        taskDetail.assignerId = task.assigner.getId();
        taskDetail.assignerImage = task.assigner.getImage();
        taskDetail.assignerName = task.assigner.getFirstName() + " " + task.assigner.getLastName();

        taskDetail.instructions = task.description;
        taskDetail.deadline = task.deadline;


            taskDetail.assigneeId = task.assignee.getId();
            taskDetail.assigneeImage = task.assignee.getImage();
            taskDetail.assigneeName = task.assignee.getName();



        if (task.proxyTeam != null) {

            taskDetail.teamId = task.proxyTeam.id;
            taskDetail.teamImage = task.proxyTeam.image;
            taskDetail.teamName = task.proxyTeam.name;

        }

        return taskDetail;

    }








    public static  List<ListMember> portUsersToListMember (List<User> users) {

        List<ListMember> listMembers = new ArrayList<>();

        for (User user : users) {

            ListMember listMember = portUsersToListMember(user);

            listMembers.add(listMember);

        }

        return listMembers;

    }



    public static ListMember portUsersToListMember (User user) {

        ListMember listMember = new ListMember();

        listMember.id = user.getId();
        listMember.image = user.getImage();
        listMember.leader = false;
        listMember.name = user.getName();
        listMember.team = false;
        listMember.userDetail = portUserToUserDetail(user);

        return listMember;

    }


    public static ListMember portTeamToListMember (Team team) {

        ListMember listMember = new ListMember();

        listMember.id = team.id;
        listMember.image = team.image;
        listMember.leader = false;
        listMember.name = team.name;
        listMember.team = true;

        TeamDetail teamDetail = new TeamDetail();

        teamDetail.id = team.id;
        teamDetail.name = team.name;
        teamDetail.image = team.image;
        teamDetail.focus = team.focus;
        teamDetail.description = team.description;
        teamDetail.members = team.memberCount;
        teamDetail.leaderId = -1;

        listMember.teamDetail = teamDetail;

        return listMember;

    }

    public static  List<ListMember> portTeamToListMember (List<Team> teams) {

        List<ListMember> listMembers = new ArrayList<>();

        for (Team team : teams) {

            ListMember listMember = portTeamToListMember(team);

            listMembers.add(listMember);

        }

        return listMembers;

    }





    public static UserDetail portUserToUserDetail (User user) {

        UserDetail userDetail = new UserDetail();

        userDetail.id = user.getId();
        userDetail.name = user.getName();
        userDetail.title = user.getTitle();
        userDetail.image = user.getImage();
        userDetail.bio = user.getDescription();



        userDetail.skills = user.getSkills();

        return userDetail;

    }



}
