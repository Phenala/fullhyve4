package com.ux7.fullhyve.services.Handlers;


import android.app.Activity;
import android.util.Log;

import com.ux7.fullhyve.services.Models.MyTeam;
import com.ux7.fullhyve.services.Utility.RequestFormat;
import com.ux7.fullhyve.services.Utility.ResponseFormat;
import com.ux7.fullhyve.services.Utility.ResponseListener;
import com.github.nkzawa.socketio.client.Ack;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamHandler extends Handler {
    public void getMyTeams(final int offset, final int limit, final Activity activity, final Runnable runnable){
        final List<MyTeam> myTeams = new ArrayList<>();
        //myTeams=cache.contacts.getMyTeams(offset,limit).toArray();

        if(myTeams.size()>0){
            activity.runOnUiThread(runnable);
        }else {
            HashMap<String, Object> args = new HashMap<>();
            args.put("offset", offset);
            args.put("limit", limit);

            JSONObject req = RequestFormat.createRequestObj("getMyTeams", args);

            socket.emit("getMyTeams", req, new Ack() {
                @Override
                public void call(Object... args) {
                    if (generalHandler(args) == 200) {
                        final ResponseFormat.GetTeamsR teamsR = gson.fromJson(args[0].toString(), ResponseFormat.GetTeamsR.class);

                        if (teamsR != null) {
                            Log.e("My teams","Fetched");
                            if(teamsR.data.myTeams.size()>0){
                                Log.e("Team name",teamsR.data.myTeams.get(0).name);
                            }

                            //cache.contacts.addReceivedMessage(friendId, {message});
                            //cache.teams.myTeams.addTeams(teamsR.data);
                        }
                        activity.runOnUiThread(runnable);
                    }
                }

            });
        }
    }


    public void searchTeams(final int offset, final int limit,String name, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("offset",offset);
        args.put("limit", limit);
        args.put("name",name);

        JSONObject req = RequestFormat.createRequestObj("searchTeams",args);

        socket.emit("searchTeams", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.GetTeamsR teamsR = gson.fromJson(args[0].toString(), ResponseFormat.GetTeamsR.class);

                    if(teamsR!=null){
                        //cache.contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = teamsR.data.message;
                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }




    public void getTeamMembers(int teamId,final int offset, final int limit, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("teamId",teamId);
        args.put("offset",offset);
        args.put("limit", limit);

        JSONObject req = RequestFormat.createRequestObj("getTeamMembers",args);

        socket.emit("getTeamMembers", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.GetTeamMemberR teamMembersR = gson.fromJson(args[0].toString(), ResponseFormat.GetTeamMemberR.class);

                    if(teamMembersR!=null){
                        Log.e("Members","Fetched");
                        if(teamMembersR.data.teamMembers != null && teamMembersR.data.teamMembers.size()>0){
                            Log.e("Member",teamMembersR.data.teamMembers.get(0).getFirstName());
                        }
                        //cache.contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = teamsR.data.message;
                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }


    public void getTeamProjects(int teamId,final int offset, final int limit, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("teamId",teamId);
        args.put("offset",offset);
        args.put("limit", limit);

        JSONObject req = RequestFormat.createRequestObj("getTeamProjects",args);

        socket.emit("getTeamProjects", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.GetTeamProjectR teamProjectsR = gson.fromJson(args[0].toString(), ResponseFormat.GetTeamProjectR.class);

                    if(teamProjectsR!=null){
                        Log.e("Team projects","Fetched");
                        if(teamProjectsR.data.teamProjects.size()>0){
                            Log.e("Project",teamProjectsR.data.teamProjects.get(0).name);
                        }
                        //cache.contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = teamsR.data.message;
                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }


    public void getTeamAnnouncements(int teamId,final int offset, final int limit, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("teamId",teamId);
        args.put("offset",offset);
        args.put("limit", limit);

        JSONObject req = RequestFormat.createRequestObj("getTeamAnnouncements",args);

        socket.emit("getTeamAnnouncements", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.GetTeamAnnouncementR teamAnnouncementsR = gson.fromJson(args[0].toString(), ResponseFormat.GetTeamAnnouncementR.class);

                    if(teamAnnouncementsR!=null){
                        //cache.contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = teamsR.data.message;
                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }

    public void announce(final int teamId, final String announcement, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("teamId",teamId);
        args.put("announcement", announcement);

        JSONObject req = RequestFormat.createRequestObj("announce",args);

        socket.emit("announce", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.AnnounceR announceR = gson.fromJson(args[0].toString(), ResponseFormat.AnnounceR.class);

                    if(announceR!=null){
                        //cache.contacts.addReceivedMessage(friendId, {message});
                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }


    public void deleteAnnouncement(final int announcementId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("announcementId",announcementId);

        JSONObject req = RequestFormat.createRequestObj("announce",args);

        socket.emit("announce", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }



    public void reply(final int teamId, final String reply, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("reply",reply);
        args.put("teamId",teamId);

        JSONObject req = RequestFormat.createRequestObj("reply",args);

        socket.emit("reply", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.ReplyR replyR = gson.fromJson(args[0].toString(), ResponseFormat.ReplyR.class);

                    if(replyR!=null){
                        //cache.contacts.addReceivedMessage(friendId, {message});
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

        socket.emit("editAnnouncementReply", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }

    public void deleteReply(final int replyId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("replyId",replyId);

        JSONObject req = RequestFormat.createRequestObj("deleteReply",args);

        socket.emit("deleteReply", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
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

        socket.emit("updateAnnouncementSeen", req, new Ack() {
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

        socket.emit("newTeam", req, new Ack() {
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
        HashMap<String, Object> args = new HashMap<>();
        args.put("teamId",teamId);

        JSONObject req = RequestFormat.createRequestObj("getMyTeamProfile",args);

        socket.emit("getMyTeamProfile", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.GetTeamProfileR teamProfileR = gson.fromJson(args[0].toString(), ResponseFormat.GetTeamProfileR.class);

                    if(teamProfileR!=null){
                        //cache.contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = teamsR.data.message;
                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }




    public void editTeamProfile(String name, String image, String description, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("name",name);
        args.put("image",image);
        args.put("description",description);

        JSONObject req = RequestFormat.createRequestObj("editTeamProfile",args);

        socket.emit("editTeamProfile", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.StatusR updateSeenR = gson.fromJson(args[0].toString(), ResponseFormat.StatusR.class);

                    if(updateSeenR!=null){
                        //cache.contacts.addReceivedMessage(friendId, {message});
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

        socket.emit("addMembers", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.StatusR updateSeenR = gson.fromJson(args[0].toString(), ResponseFormat.StatusR.class);

                    if(updateSeenR!=null){
                        //cache.contacts.addReceivedMessage(friendId, {message});
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

        socket.emit("removeMembers", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
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

        socket.emit("replyTeamJoinRequest", req, new Ack() {
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

        socket.emit("deleteTeam", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }
}
