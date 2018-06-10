package com.ux7.fullhyve.services.Utility;

public class Constant {
    public final String DOMAIN_NAME = "localhost";

    // IMAGE
    public final String UP_DOWNLOAD_DOMAIN = "/file";
    public final String UPLOAD_IMAGE = UP_DOWNLOAD_DOMAIN + "/upload";
    public final String GET_IMAGE = UP_DOWNLOAD_DOMAIN;

    // ACCOUNT
    public final String ACCOUNT_DOMAIN = "/account";
    public final String SIGNIN = ACCOUNT_DOMAIN + "signin";
    public final String SIGNUP = ACCOUNT_DOMAIN + "/signup";
    public final String SIGNOUT = ACCOUNT_DOMAIN + "/signout";
    public final String GET_MY_PROFILE = ACCOUNT_DOMAIN + "/getMyProfile";
    public final String EDIT_PROFILE = ACCOUNT_DOMAIN + "/editProfile";
    public final String SEND_FRIEND_REQUEST = ACCOUNT_DOMAIN + "/addFriend";
    public final String ACCEPT_FRIEND_REQUEST = ACCOUNT_DOMAIN + "/replyFriendRequest/Accept";
    public final String REJECT_FRIEND_REQEUST = ACCOUNT_DOMAIN + "/replyFriendRequest/Reject";
    public final String UNFRIEND = ACCOUNT_DOMAIN + "/unfriend";
    public final String GET_NOTIFICATIONS = ACCOUNT_DOMAIN + "/getNotifications";


    // CHAT
    public final String CHAT_DOMAIN = "/chat";
    public final String SEND_MESSAGE = CHAT_DOMAIN + "sendMessage";
    public final String EDIT_MESSAGE= CHAT_DOMAIN + "/editMessage";
    public final String FORWARD_MESSAGE = CHAT_DOMAIN + "/forwardMessage";
    public final String DELETE_MESSAGE = CHAT_DOMAIN + "/deleteMessage";
    public final String UPDATE_MESSAGE_SEEN = CHAT_DOMAIN + "/updateMessageSeen";
    public final String GET_FRIEND_LAST_SEEN_TIME = CHAT_DOMAIN + "/getFriendLastSeenTime";
    public final String GET_MESSAGES = CHAT_DOMAIN + "/getMessages";
    public final String GET_FRIENDS = CHAT_DOMAIN + "/getFriends";
    public final String SEARCH_USERS = CHAT_DOMAIN + "/searchUsers";


    // TEAM
    public final String TEAM_DOMAIN = "/team";
    public final String GET_MY_TEAMS = TEAM_DOMAIN + "/getMyTeams";
    public final String SEARCH_TEAMS = TEAM_DOMAIN + "/searchTeams";
    public final String GET_TEAM_MEMBERS = TEAM_DOMAIN + "/getTeamMembers";
    public final String GET_TEAM_PROJECTS = TEAM_DOMAIN + "/getTeamProjects";
    public final String GET_TEAM_ANNOUNCEMENTS = TEAM_DOMAIN + "/getTeamAnnouncements";
    public final String ANNOUNCE = TEAM_DOMAIN + "/announce";
    public final String DELETE_ANNOUNCEMENT = TEAM_DOMAIN + "/deleteAnnouncement";
    public final String REPLY = TEAM_DOMAIN + "/reply";
    public final String EDIT_ANNOUNCEMENT_REPLY = TEAM_DOMAIN + "/editAnnouncementReply";
    public final String DELETE_REPLY = TEAM_DOMAIN + "/deleteReply";
    public final String UPDATE_ANNOUNCEMENT_SEEN = TEAM_DOMAIN + "/updateAnnouncementSeen";
    public final String NEW_TEAM = TEAM_DOMAIN + "/newTeam";
    public final String GET_MY_TEAM_PROFILE = TEAM_DOMAIN + "/getMyTeamProfile";
    public final String EDIT_TEAM_PROFILE = TEAM_DOMAIN + "/editTeamProfile";
    public final String ADD_MEMBERS = TEAM_DOMAIN + "/addMembers";
    public final String REMOVE_MEMBERS = TEAM_DOMAIN + "/removeMembers";
    public final String ACCEPT_TEAM_JOIN_REQUEST = TEAM_DOMAIN + "/replyTeamJoinRequest/Accept";
    public final String REJECT_TEAM_JOIN_REQUEST = TEAM_DOMAIN + "/replyTeamJoinRequest/Reject";
    public final String DELETE_TEAM = TEAM_DOMAIN + "/deleteTeam";


    // PROJECT
    public final String PROJECT_DOMAIN = "/project";
    public final String GET_MY_PROJECTS = PROJECT_DOMAIN + "/getMyProjects";
    public final String SEARCH_PROJECTS = PROJECT_DOMAIN + "/searchProjects";
    public final String GET_CONTRIBUTORS = PROJECT_DOMAIN + "/getContributors";
    public final String GET_TASKSETS = PROJECT_DOMAIN + "/getTasksets";
    public final String GET_TASKS = PROJECT_DOMAIN + "/getTasks";
    public final String NEW_PROJECT = PROJECT_DOMAIN + "/newProject";
    public final String ADD_CONTRIBUTORS = PROJECT_DOMAIN + "/addContributors";
    public final String REMOVE_CONTRIBUTORS = PROJECT_DOMAIN + "/removeContributors";
    public final String ACCEPT_INDIVIDUAL_CONTRIBUTOR_JOIN_REQUEST = PROJECT_DOMAIN + "/replyIndividualContributorJoinRequest/Accept";
    public final String REJECT_INDIVIDUAL_CONTRIBUTOR_JOIN_REQUEST = PROJECT_DOMAIN + "/replyIndividualContributorJoinRequest/Reject";
    public final String ACCEPT_TEAM_CONTRIBUTOR_JOIN_REQUEST = PROJECT_DOMAIN + "/replyTeamContributorJoinRequest/Accept";
    public final String REJECT_TEAM_CONTRIBUTOR_JOIN_REQUEST = PROJECT_DOMAIN + "/replyTeamContributorJoinRequest/Reject";
    public final String GET_MY_PROJECT_DETAIL = PROJECT_DOMAIN + "/getMyProjectDetails";
    public final String EDIT_PROJECT_DETAILS = PROJECT_DOMAIN + "/editProjectDetails";
    public final String DELETE_PROJECT = PROJECT_DOMAIN + "/deleteProject";

    public final String NEW_TASKSET = PROJECT_DOMAIN + "/newTaskset";
    public final String DELETE_TASKSET = PROJECT_DOMAIN + "/deleteTaskset";

    public final String NEW_TASK = PROJECT_DOMAIN + "/newTask";
    public final String START_TASK = PROJECT_DOMAIN + "/startTask";
    public final String COMPLETE_TASK = PROJECT_DOMAIN + "/completeTask";
    public final String APPROVE_TASK = PROJECT_DOMAIN + "/changeTaskStatus/Approve";
    public final String REVISE_TASK = PROJECT_DOMAIN + "/changeTaskStatus/Revise";
    public final String DELETE_TASK = PROJECT_DOMAIN + "/deleteTask";
}
