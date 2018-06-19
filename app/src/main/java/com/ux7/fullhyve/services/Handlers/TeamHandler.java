package com.ux7.fullhyve.services.Handlers;


import android.app.Activity;
import android.util.Log;

import com.ux7.fullhyve.services.Models.Announcement;
import com.ux7.fullhyve.services.Models.MyProject;
import com.ux7.fullhyve.services.Models.MyTeam;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.services.Models.Project;
import com.ux7.fullhyve.services.Models.SendAnnouncement;
import com.ux7.fullhyve.services.Models.Team;
import com.ux7.fullhyve.services.Utility.Converter;
import com.ux7.fullhyve.services.Models.User;
import com.ux7.fullhyve.services.Utility.Realtime;
import com.ux7.fullhyve.services.Utility.RequestFormat;
import com.ux7.fullhyve.services.Utility.ResponseFormat;
import com.ux7.fullhyve.services.Utility.ResponseListener;
import com.github.nkzawa.socketio.client.Ack;
import com.ux7.fullhyve.ui.data.ListAnnouncement;
import com.ux7.fullhyve.ui.data.ListMember;
import com.ux7.fullhyve.ui.data.ListProject;
import com.ux7.fullhyve.ui.data.ListReply;
import com.ux7.fullhyve.ui.data.ListTeam;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

public class TeamHandler extends Handler {
    public void getMyTeams(final int offset, final int limit, final List<ListTeam> teams, final Activity activity, final Runnable runnable){

        List<MyTeam> myTeams = new ArrayList<>();
        //myTeams=AppData.getCache().contacts.getMyTeams(offset,limit).toArray();

        myTeams =AppData.getCache().getTeams().getMyTeams(offset,limit);

        if(!Realtime.socket.connected()){
            Log.e("Called","Local");
            teams.clear();
            teams.addAll(Converter.portMyTeamToListTeam(myTeams));
            activity.runOnUiThread(runnable);
        }else {
            HashMap<String, Object> args = new HashMap<>();
            args.put("offset", offset);
            args.put("limit", limit);

            JSONObject req = RequestFormat.createRequestObj("getMyTeams", args);

            Realtime.socket.emit("getMyTeams", req, new Ack() {
                @Override
                public void call(Object... args) {
                    if (generalHandler(args) == 200) {
                        final ResponseFormat.GetTeamsR teamsR = gson.fromJson(args[0].toString(), ResponseFormat.GetTeamsR.class);

                        if (teamsR != null && teamsR.data.myTeams != null) {
                            Log.e("Teams size",teamsR.data.myTeams.size()+"");
                            AppData.getCache().getTeams().addTeams((ArrayList<MyTeam>) teamsR.data.myTeams);
                        }

                        Log.e("Got teams ", "Length of " + teamsR.data.myTeams.size());

                        teams.clear();
                        teams.addAll(Converter.portMyTeamToListTeam(teamsR.data.myTeams));

                        activity.runOnUiThread(runnable);
                    }
                }

            });
        }
    }


    public void searchTeams(final int offset, final int limit, String name, final List<ListTeam> listTeams, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("offset",offset);
        args.put("limit", limit);
        args.put("name",name);

        JSONObject req = RequestFormat.createRequestObj("searchTeams",args);

        Realtime.socket.emit("searchTeams", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.SearchTeamsR teamsR = gson.fromJson(args[0].toString(), ResponseFormat.SearchTeamsR.class);

                    if(teamsR!=null){
                        //AppData.getCache().contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = teamsR.data.message;
                    }

                    listTeams.clear();
                    listTeams.addAll(Converter.portMyTeamToListTeam(teamsR.data.myTeams));
                    listTeams.addAll(Converter.portTeamToListTeam(teamsR.data.teams));

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }

    public void searchAddTeams(final int offset, final int limit, String name, final List<ListMember> listMembers, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("offset",offset);
        args.put("limit", limit);
        args.put("name",name);

        JSONObject req = RequestFormat.createRequestObj("searchTeams",args);

        Realtime.socket.emit("searchTeams", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.SearchTeamsR teamsR = gson.fromJson(args[0].toString(), ResponseFormat.SearchTeamsR.class);

                    if(teamsR!=null){
                        //AppData.getCache().contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = teamsR.data.message;
                    }

                    listMembers.clear();
                    listMembers.addAll(Converter.portTeamToListMember(teamsR.data.teams));

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }


    public void getTeamMembers(final int teamId, final int offset, final int limit, final List<ListMember> members, final Activity activity, final Runnable runnable){

        MyTeam team = AppData.getCache().getTeams().getTeam(teamId);
        List<User> teams = null;

        if(team!=null){
            teams = team.getMembers(offset, limit);
        }

        if(!Realtime.socket.connected()){
            members.clear();
            members.addAll(Converter.portUsersToListMember(teams));
            activity.runOnUiThread(runnable);
        } else{
            HashMap<String, Object> args = new HashMap<>();
            args.put("teamId",teamId);
            args.put("offset",offset);
            args.put("limit", limit);

            JSONObject req = RequestFormat.createRequestObj("getTeamMembers",args);

            Realtime.socket.emit("getTeamMembers", req, new Ack() {
                @Override
                public void call(Object... args) {
                    if(generalHandler(args)==200) {
                        final ResponseFormat.GetTeamMemberR membersR = gson.fromJson(args[0].toString(), ResponseFormat.GetTeamMemberR.class);

                        if (membersR != null) {
                            if (membersR != null && membersR.data.members != null) {
                                Log.e("Members size", membersR.data.members.size() + "");
                                if (AppData.getCache().getTeams().getTeam(teamId) != null) {
                                    AppData.getCache().getTeams().getTeam(teamId).addMembers(membersR.data.members);
                                }
                            }

                            members.clear();
                            members.addAll(Converter.portUsersToListMember(membersR.data.members));

                        }

                        activity.runOnUiThread(runnable);
                    }
                }
            });
        }


    }

    public void getTeamProjects(final int teamId, final int offset, final int limit, final List<ListProject> listProjects, final Activity activity, final Runnable runnable){
        MyTeam team = AppData.getCache().getTeams().getTeam(teamId);
        List<Project> projects = null;

        if(team!=null){
            projects = team.getProjects(offset, limit);
        }

        if(!Realtime.socket.connected()){
            listProjects.clear();
            listProjects.addAll(Converter.portProjectToListProject(projects));

            activity.runOnUiThread(runnable);
        } else {
            HashMap<String, Object> args = new HashMap<>();
            args.put("teamId",teamId);
            args.put("offset",offset);
            args.put("limit", limit);

            JSONObject req = RequestFormat.createRequestObj("getTeamProjects",args);

            Realtime.socket.emit("getTeamProjects", req, new Ack() {
                @Override
                public void call(Object... args) {
                    if(generalHandler(args)==200){
                        final ResponseFormat.GetTeamProjectR teamProjectsR = gson.fromJson(args[0].toString(), ResponseFormat.GetTeamProjectR.class);
                        Log.e("team projecto", teamProjectsR.data.projects.size() + "");

                        if(teamProjectsR!=null && teamProjectsR.data.projects != null){
                            Log.e("Team projects","Fetched");
//                            if(AppData.getCache().getTeams().getTeam(teamId)!=null){
//                                AppData.getCache().getTeams().getTeam(teamId).addProjects(teamProjectsR.data.projects);
//                            }

                            listProjects.clear();
                            listProjects.addAll(Converter.portProjectToListProject(teamProjectsR.data.projects));

                        }

                        activity.runOnUiThread(runnable);
                    }
                }
            });
        }
    }


    public void getTeamAnnouncements(final int teamId, final int offset, final int limit, final List<ListAnnouncement> listAnnouncements, final Activity activity, final Runnable runnable){
        MyTeam team = AppData.getCache().getTeams().getTeam(teamId);
        List<Announcement> announcements = null;

        if(team!=null){
            announcements = team.getAnnouncements(offset, limit);
        }

        if(!Realtime.socket.connected()){
            listAnnouncements.clear();
            listAnnouncements.addAll(Converter.portAnnouncementToListAnnouncement(announcements));

            activity.runOnUiThread(runnable);
        } else {
            HashMap<String, Object> args = new HashMap<>();
            args.put("teamId",teamId);
            args.put("offset",offset);
            args.put("limit", limit);

            JSONObject req = RequestFormat.createRequestObj("getTeamAnnouncements",args);

            Realtime.socket.emit("getTeamAnnouncements", req, new Ack() {
                @Override
                public void call(Object... args) {
                    if(generalHandler(args)==200){
                        final ResponseFormat.GetTeamAnnouncementR teamAnnouncementsR = gson.fromJson(args[0].toString(), ResponseFormat.GetTeamAnnouncementR.class);

                        if(teamAnnouncementsR!=null && teamAnnouncementsR.data.announcements!=null){
//                            if(AppData.getCache().getTeams().getTeam(teamId) != null){
//                                AppData.getCache().getTeams().getTeam(teamId).addNewAnnouncements(teamAnnouncementsR.data.announcements);
//                            }

                            listAnnouncements.clear();
                            listAnnouncements.addAll(Converter.portAnnouncementToListAnnouncement(teamAnnouncementsR.data.announcements));

                        }

                        activity.runOnUiThread(runnable);
                    }
                }
            });
        }
    }

    public void announce(final int teamId, final String announcement, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("teamId",teamId);
        args.put("message", announcement);

        JSONObject req = RequestFormat.createRequestObj("announce",args);

        final int tempId = AppData.getCache().getTeams().addSendAnnouncement(new SendAnnouncement(teamId, announcement));

        Realtime.socket.emit("announce", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.AnnounceR announceR = gson.fromJson(args[0].toString(), ResponseFormat.AnnounceR.class);

                    if(announceR!=null && announceR.data != null){

                        // add the current user as the sender of the announcement
                        announceR.data.mainMessage.sender = AppData.getCache().getIdentity();

                        AppData.getCache().getTeams().removeSendAnnouncement(tempId);

//                        if(AppData.getCache().getTeams().getTeam(teamId) != null){
//                            AppData.getCache().getTeams().getTeam(teamId).addAnnouncement(announceR.data);
//                        }
                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }


    public void deleteAnnouncement(final int teamId, final int announcementId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("announcementId",announcementId);

        JSONObject req = RequestFormat.createRequestObj("deleteAnnouncement",args);

        Realtime.socket.emit("deleteAnnouncement", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){

//                    if(AppData.getCache().getTeams().getTeam(teamId) != null){
//                        AppData.getCache().getTeams().getTeam(teamId).removeAnnouncement(announcementId);
//                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }



    public void reply(final int teamId, final String reply, final int mainAnnouncementId, final ListReply listReply, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("message",reply);
        args.put("teamId",teamId);
        args.put("mainAnnouncementId", mainAnnouncementId);

        JSONObject req = RequestFormat.createRequestObj("replyAnnouncement",args);

        Realtime.socket.emit("replyAnnouncement", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.AnnounceR replyR = gson.fromJson(args[0].toString(), ResponseFormat.AnnounceR.class);

                    if(replyR!=null && replyR.data!=null){
                        replyR.data.mainMessage.sender = AppData.getCache().getIdentity();

                        MyTeam team = AppData.getCache().getTeams().getTeam(teamId);

                        if(team != null && team.getAnnouncement(mainAnnouncementId) != null){
                            team.getAnnouncement(mainAnnouncementId).addReply(replyR.data.mainMessage);
                        }

                        //listReply.id = replyR.data.replyId;
                    }


                    activity.runOnUiThread(runnable);
                }
            }
        });
    }


    public void editAnnouncementReply(final int announcementId, final String newAnnouncement, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("announcementId",announcementId);
        args.put("newAnnouncement",newAnnouncement);

        JSONObject req = RequestFormat.createRequestObj("editAnnouncementReply",args);

        Realtime.socket.emit("editAnnouncementReply", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }

    public void deleteReply(final int teamId, final int mainAnnouncementId, final int replyId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("replyId",replyId);

        JSONObject req = RequestFormat.createRequestObj("deleteReply",args);

        Realtime.socket.emit("deleteReply", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    MyTeam team = AppData.getCache().getTeams().getTeam(teamId);

//                    if(team != null && team.getAnnouncement(mainAnnouncementId) != null){
//                        team.getAnnouncement(mainAnnouncementId).removeReply(replyId);
//                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }




    public void updateAnnouncementSeen(final int teamId,final int lastAnnId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("teamId",teamId);
        args.put("lastAnnId",lastAnnId);

        JSONObject req = RequestFormat.createRequestObj("updateAnnouncementSeen",args);

        Realtime.socket.emit("updateAnnouncementSeen", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }



    public void newTeam(final String name,final String image, final String focus,final String description, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("name",name);
        args.put("image",image);
        args.put("focus",focus);
        args.put("description",description);

        JSONObject req = RequestFormat.createRequestObj("newTeam",args);

        Realtime.socket.emit("newTeam", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.CreateNewTeamR newTeamR = gson.fromJson(args[0].toString(), ResponseFormat.CreateNewTeamR.class);

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }


    public void getTeamProfile(int teamId, final Activity activity, final Runnable runnable){
        Team team = AppData.getCache().getTeams().getTeam(teamId);

        if(team != null){
            activity.runOnUiThread(runnable);
        } else{
            HashMap<String, Object> args = new HashMap<>();
            args.put("teamId",teamId);

            JSONObject req = RequestFormat.createRequestObj("getMyTeamProfile",args);

            Realtime.socket.emit("getMyTeamProfile", req, new Ack() {
                @Override
                public void call(Object... args) {
                    if(generalHandler(args)==200){
                        final ResponseFormat.GetTeamProfileR teamProfileR = gson.fromJson(args[0].toString(), ResponseFormat.GetTeamProfileR.class);

                        if(teamProfileR!=null){
                        }

                        activity.runOnUiThread(runnable);
                    }
                }
            });
        }


    }




    public void editTeamProfile(String name, String image, String description, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("name",name);
        args.put("image",image);
        args.put("description",description);

        JSONObject req = RequestFormat.createRequestObj("editTeamProfile",args);

        Realtime.socket.emit("editTeamProfile", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.StatusR updateSeenR = gson.fromJson(args[0].toString(), ResponseFormat.StatusR.class);

                    if(updateSeenR!=null){
                        //AppData.getCache().contacts.addReceivedMessage(friendId, {message});
                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }




    public void addMembers(final int teamId, final int[] memberIds, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("teamId",teamId);
        args.put("memberIds",memberIds);

        JSONObject req = RequestFormat.createRequestObj("addMembers",args);

        Realtime.socket.emit("addMembers", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.StatusR updateSeenR = gson.fromJson(args[0].toString(), ResponseFormat.StatusR.class);

                    if(updateSeenR!=null){
                        //AppData.getCache().contacts.addReceivedMessage(friendId, {message});
                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }


    public void removeMembers(final int teamId, final int[] memberIds, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("teamId",teamId);
        args.put("memberIds",memberIds);

        JSONObject req = RequestFormat.createRequestObj("addMembers",args);

        Realtime.socket.emit("removeMembers", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    if(AppData.getCache().getTeams().getTeam(teamId) != null){
                        AppData.getCache().getTeams().getTeam(teamId).removeMember(memberIds);
                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }



    public void replyTeamJoinRequest(final int requestId, final boolean accepted, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("requestId",requestId);
        args.put("accepted", accepted);

        JSONObject req = RequestFormat.createRequestObj("replyTeamJoinRequest",args);

        Realtime.socket.emit("replyTeamJoinRequest", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }




    public void deleteTeam(final int teamId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("teamId",teamId);

        JSONObject req = RequestFormat.createRequestObj("deleteTeam",args);

        Realtime.socket.emit("deleteTeam", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    if(AppData.getCache().getTeams().getTeam(teamId) != null){
                        AppData.getCache().getTeams().removeTeam(teamId);
                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }
}
